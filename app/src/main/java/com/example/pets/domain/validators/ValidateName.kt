package com.example.pets.domain.validators

class ValidateName {

    fun execute(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name field can't be empty"
            )
        }
        if (name.length > 12) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't have more than 12 characters"
            )
        }
        if (name.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name needs more thant 1 character"
            )
        }
        return ValidationResult(successful = true)
    }
}