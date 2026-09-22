package com.example.casocerrado.data.model

// enum basico de tipos de evidencia
enum class TypeEvidence(val label: String) {
    PHOTO("Photo"),
    DOCUMENT("Document"),
    TESTIMONY("Testimony"),
    OBJECT("Object"),
    OTHER("Other");

    companion object {
        fun fromName(name: String): TypeEvidence {
            for (tipo in entries) {
                if (tipo.name == name) {
                    return tipo
                }
            }
            return OTHER
        }
    }
}