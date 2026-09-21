package com.example.casocerrado.data.model


data class Case(
    var id: Int = 0,
    var title: String,
    var description: String,
    var fecha: String,
    var state: EstateCase = EstateCase.IN_INVESTIGATION,

    var finding: MutableList<Finding> = mutableListOf(),
    var evidence: MutableList<Evidence> = mutableListOf()
) {
    //Funcion para comparar el enum aca y no afuera en cada pantalla

    fun itsClosed(): Boolean {
        return state.isClosed
    }


    //Funcion que compara el texto con lo que se escribe para el componente del buscador
    fun matchWithSearch(text: String): Boolean {
        return title.lowercase().contains(text.lowercase())
    }
}