package com.example.pets.domain.validators

class ValidateBreed {

    fun execute(breed: String): ValidationResult {
        if (breed.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Breed field can't be empty"
            )
        }
        return ValidationResult(successful = true)
    }
}