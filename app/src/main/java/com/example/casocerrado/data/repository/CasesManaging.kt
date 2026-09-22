package com.example.casocerrado.data.repository

import com.example.casocerrado.data.model.Case
import com.example.casocerrado.domain.CasesRepository

class CasesManaging : CasesRepository {

    private var listaCasos: MutableList<Case> = mutableListOf()

    private var siguienteId: Int = 1

    override fun getAll(): List<Case> {
        return listaCasos
    }

    override fun getById(id: Int): Case? {
        for (caso in listaCasos) {
            if (caso.id == id) {
                return caso
            }
        }
        return null
    }

    override fun save(case: Case): Case {

        if (case.id == 0) {
            case.id = siguienteId
            siguienteId = siguienteId + 1
            listaCasos.add(case)
            return case
        }

        var yaExiste = false
        for (i in listaCasos.indices) {
            if (listaCasos[i].id == case.id) {
                listaCasos[i] = case
                yaExiste = true
            }
        }

        if (!yaExiste) {
            listaCasos.add(case)
        }

        return case
    }

    override fun delete(id: Int): Boolean {
        val caso = getById(id)

        if (caso == null) {
            return false
        }

        listaCasos.remove(caso)
        return true
    }
}