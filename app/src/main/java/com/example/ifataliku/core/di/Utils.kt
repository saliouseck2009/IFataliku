package com.example.ifataliku.core.di

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import androidx.compose.ui.graphics.Color
import com.example.ifataliku.data.datasource.local.entities.Souvenir
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object Utils {
    fun getCurrentDate(format: String = "dd MMM yyyy"): String {
        val formatter = SimpleDateFormat(format)
        return formatter.format(Date())
    }

    fun getFormattedDate(date: String, outputFormat: String = "dd MMM yyyy"): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(date)
        val formatter = SimpleDateFormat(outputFormat)
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

    fun groupAndSortSouvenirs(souvenirs: List<Souvenir>): Pair<List<Souvenir>, Map<String, List<Souvenir>>> {
        // Define date formatter for parsing and grouping
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val monthYearFormatter = SimpleDateFormat("MMM yyyy", Locale.getDefault())

        // Parse and sort the souvenirs by date descending
        val sortedList = souvenirs.sortedByDescending { dateFormatter.parse(it.date) }

        // Group the sorted list by "MMM yyyy"
        val groupedByMonthYear = sortedList.groupBy {
            val date = dateFormatter.parse(it.date)
            monthYearFormatter.format(date!!)
        }

        return Pair(sortedList, groupedByMonthYear)
    }





}

object LocationUtils{
    fun getImageLocation(context: Context, imageUri: Uri): Pair<Double?, Double?> {
        var latitude: Double? = null
        var longitude: Double? = null

        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            inputStream?.use { stream ->
                val exifInterface = ExifInterface(stream)

                val latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                val latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF)
                val lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE)
                val lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF)

                if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
                    latitude = if (latRef == "N") {
                        convertRationalLatLonToDeg(latValue)
                    } else {
                        -convertRationalLatLonToDeg(latValue)
                    }
                    longitude = if (lngRef == "E") {
                        convertRationalLatLonToDeg(lngValue)
                    } else {
                        -convertRationalLatLonToDeg(lngValue)
                    }
                }
            }
        } catch (e: Exception) {
            // Handle exceptions (e.g., file not found, invalid URI)
            e.printStackTrace()
        }
        return if(latitude == null || longitude == null){
            Pair(null, null)
        }else{
            Pair(latitude, longitude)
        }
    }

    // Helper function to convert rational latitude/longitude to degrees
    private fun convertRationalLatLonToDeg(rationalString: String): Double {
        val parts = rationalString.split(",")
        val degrees = parts[0].split("/")[0].toDouble() / parts[0].split("/")[1].toDouble()
        val minutes = parts[1].split("/")[0].toDouble() / parts[1].split("/")[1].toDouble()
        val seconds = parts[2].split("/")[0].toDouble() / parts[2].split("/")[1].toDouble()

        return degrees + (minutes / 60.0) + (seconds / 3600.0)
    }
}

fun String.asColor(): Color{
    return Utils.getColorFromHexString(this)
}

fun <T> List<T>.safeSublist(start: Int, end: Int): List<T> {
    val safeStart = start.coerceIn(0, this.size)  // Ensure start is within bounds
    val safeEnd = end.coerceIn(0, this.size)      // Ensure end is within bounds

    if (safeStart > safeEnd) return emptyList()    // Return empty if start > end

    return this.subList(safeStart, safeEnd)        // Return the sublist based on valid indices
}