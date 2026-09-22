package com.example.casocerrado.data.model

import java.time.LocalDate

data class Finding(
    val id: Int = 0,
    val caseId: Int,
    val title: String,
    val description: String,
    val date: LocalDate = LocalDate.now()
) {

    fun isValid(): Boolean = title.isNotBlank() && description.isNotBlank()
}