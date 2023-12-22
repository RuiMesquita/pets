package com.example.pets.domain.validators

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

class ValidateHour {

    @RequiresApi(Build.VERSION_CODES.O)
    fun execute(hour: String): ValidationResult {
        if (hour.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Hour field can't be empty"
            )
        }

        try {
            LocalTime.parse(hour)
        }
        catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = "Invalid time"
            )
        }

        return ValidationResult(successful = true)
    }
}