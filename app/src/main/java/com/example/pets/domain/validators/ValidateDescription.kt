package com.example.pets.domain.validators

class ValidateDescription {

    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Description field can't be empty"
            )
        }
        if (name.length > 50) {
            return ValidationResult(
                successful = false,
                errorMessage = "Description can't have more than 12 characters"
            )
        }
        if (name.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "Description needs more thant 1 character"
            )
        }
        return ValidationResult(successful = true)
    }
}