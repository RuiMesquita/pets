package com.example.pets.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

object Constants {

    @RequiresApi(Build.VERSION_CODES.O)
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val daySuffixes = listOf(
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
        "th", "st"
    )
}