package com.example.pets.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.Date
import java.util.Locale

class DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        fun calculateAge(dateOfBirth: LocalDate): Int {
            val period = Period.between(dateOfBirth, LocalDate.now())
            return period.years
        }

        fun convertMillisToDate(millis: Long?): String {
            if (millis != null) {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                return formatter.format(Date(millis))
            }
            else {
                return ""
            }
        }
    }
}