package com.example.casocerrado.data.model

import java.time.LocalDate

data class Evidence(
    val id: Int = 0,
    val caseId: Int,
    val type: TypeEvidence,
    val description: String,
    val date: LocalDate = LocalDate.now()
) {

    fun isValid(): Boolean = description.isNotBlank()
}
