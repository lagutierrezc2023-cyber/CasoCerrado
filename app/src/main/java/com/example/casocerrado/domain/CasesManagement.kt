package com.example.casocerrado.domain

import com.example.casocerrado.data.model.Case
import com.example.casocerrado.data.model.EstateCase
import com.example.casocerrado.data.model.Evidence
import com.example.casocerrado.data.model.Finding
import com.example.casocerrado.data.model.TypeEvidence


interface CasesRepository {
    fun getAll(): List<Case>
    fun getById(id: Int): Case?
    fun save(case: Case): Case
    fun delete(id: Int): Boolean
}

class CasesManagement(private val repository: CasesRepository) {



    fun getCases(): List<Case> {
        return repository.getAll()
    }

    fun getCase(id: Int): Case? {
        return repository.getById(id)
    }


    fun searchCases(query: String, state: EstateCase? = null): List<Case> {
        val result = mutableListOf<Case>()

        for (case in repository.getAll()) {
            val matchesText = query.isBlank() || case.matchWithSearch(query)
            val matchesState = state == null || case.state == state

            if (matchesText && matchesState) {
                result.add(case)
            }
        }
        return result
    }



    fun createCase(title: String, description: String, date: String): String {
        val error = validateCase(title, description, date)
        if (error != "") {
            return error
        }

        val newCase = Case(
            title = title.trim(),
            description = description.trim(),
            fecha = date.trim(),
            state = EstateCase.IN_INVESTIGATION
        )
        repository.save(newCase)
        return ""
    }

    fun editCase(id: Int, title: String, description: String, date: String): String {
        val existing = repository.getById(id)
        if (existing == null) {
            return "Case not found"
        }

        val error = validateCase(title, description, date)
        if (error != "") {
            return error
        }

        existing.title = title.trim()
        existing.description = description.trim()
        existing.fecha = date.trim()
        repository.save(existing)
        return ""
    }

    fun deleteCase(id: Int): String {
        val deleted = repository.delete(id)
        if (!deleted) {
            return "Case not found"
        }
        return ""
    }



    fun addFinding(caseId: Int, title: String, description: String): String {
        val existing = repository.getById(caseId)
        if (existing == null) {
            return "Case not found"
        }
        if (existing.state.isClosed) {
            return "A closed case cannot receive new findings"
        }

        val finding = Finding(
            id = existing.finding.size + 1,
            caseId = caseId,
            title = title.trim(),
            description = description.trim()
        )
        if (!finding.isValid()) {
            return "A finding needs a title and a description"
        }

        existing.finding.add(finding)
        repository.save(existing)
        return ""
    }

    fun addEvidence(caseId: Int, type: TypeEvidence, description: String): String {
        val existing = repository.getById(caseId)
        if (existing == null) {
            return "Case not found"
        }
        if (existing.state.isClosed) {
            return "A closed case cannot receive new evidence"
        }

        val evidence = Evidence(
            id = existing.evidence.size + 1,
            caseId = caseId,
            type = type,
            description = description.trim()
        )
        if (!evidence.isValid()) {
            return "Evidence needs a description"
        }

        existing.evidence.add(evidence)
        repository.save(existing)
        return ""
    }

    fun closeCase(id: Int): String {
        val existing = repository.getById(id)
        if (existing == null) {
            return "Case not found"
        }
        if (existing.state.isClosed) {
            return "The case is already closed"
        }

        existing.state = EstateCase.CLOSED
        repository.save(existing)
        return ""
    }

    fun reopenCase(id: Int): String {
        val existing = repository.getById(id)
        if (existing == null) {
            return "Case not found"
        }
        if (!existing.state.isClosed) {
            return "The case is not closed"
        }

        existing.state = EstateCase.IN_INVESTIGATION
        repository.save(existing)
        return ""
    }

    private fun validateCase(title: String, description: String, date: String): String {
        if (title.isBlank()) {
            return "The title is required"
        }
        if (description.isBlank()) {
            return "The description is required"
        }
        if (date.isBlank()) {
            return "The date is required"
        }
        return ""
    }
}