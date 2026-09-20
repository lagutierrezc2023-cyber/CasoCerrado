package com.example.casocerrado.data.model

enum class EstateCase(val label: String) {
    IN_INVESTIGATION("In investigation"),
    CLOSED("Closed");

    val isClosed: Boolean
        get() = this == CLOSED

    companion object {
        /** Rebuilds the status from the stored value (text in SQLite / SharedPreferences). */
        fun fromName(name: String): EstateCase =
            entries.firstOrNull { it.name == name } ?: IN_INVESTIGATION
    }
}