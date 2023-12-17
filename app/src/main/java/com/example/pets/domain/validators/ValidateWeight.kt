package com.example.pets.domain.validators

class ValidateWeight {

    fun execute(weight: String): ValidationResult {
        if (weight.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Weight field can't be empty"
            )
        }
        try {
            weight.toFloat()
        } catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid weight value"
            )
        }
        return ValidationResult(successful = true)
    }
}