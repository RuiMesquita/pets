package com.example.pets.domain.validators

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pets.common.Constants
import java.time.LocalDate

class ValidateDateOfBirth {

    @RequiresApi(Build.VERSION_CODES.O)
    fun execute(date: String): ValidationResult {
        if (date.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Date of birth field can't be empty"
            )
        }
        try {
            val dateTime = LocalDate.parse(date, Constants.DATE_FORMATTER)
            if (dateTime.isAfter(LocalDate.now())) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Invalid date"
                )
            }

        } catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid date"
            )
        }
        return ValidationResult(successful = true)
    }
}