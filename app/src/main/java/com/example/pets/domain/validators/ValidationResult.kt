package com.example.pets.domain.validators

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
