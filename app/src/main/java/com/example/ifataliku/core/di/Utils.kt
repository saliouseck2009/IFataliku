package com.example.ifataliku.core.di

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object Utils {
    fun getCurrentDate(format: String = "dd MMM yyyy"): String {
        val formatter = SimpleDateFormat(format)
        return formatter.format(Date())
    }

    fun getFormattedDate(date: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(date)
        val formatter = SimpleDateFormat("dd MMM yyyy")
        return formatter.format(date)
    }

    fun getDateFromString(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(date, formatter)
    }

    fun getMillisDate(date: LocalDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now())):
            Long {
        val zoneId = ZoneId.systemDefault()
        return date.atZone(zoneId).toInstant().toEpochMilli()
    }

    fun getFrFormattedDate(date: String, separator: String = "-"): String {
        val dateSplit = date.split("-")
        val day = dateSplit[2]
        val month = dateSplit[1]
        val year = dateSplit[0]
        return "$day$separator$month$separator$year"
    }

    fun getColorFromHexString(hexStringColor: String): Color {
        return Color(
            "FF${hexStringColor}"
                .toLong(radix = 16).toInt()
        )
    }

    fun String.asColor(): Color{
        return getColorFromHexString(this)
    }

}