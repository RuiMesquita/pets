package com.example.pets.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period

class DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        fun calculateAge(dateOfBirth: LocalDate): Int {
            val period = Period.between(dateOfBirth, LocalDate.now())
            return period.years
        }
    }

}