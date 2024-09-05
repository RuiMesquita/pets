package com.example.pets.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
object Constants {

    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val DAY_SUFFIXES = listOf(
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
        "th", "st"
    )
}