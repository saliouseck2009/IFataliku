package com.example.ifataliku.data.datasource

import androidx.room.TypeConverter
import com.google.gson.Gson


data class LabelledColor(val label: String, val color: String)

class LabelledColorConverter{
    private val gson: Gson = Gson()
    @TypeConverter
    fun fromLabelledColor(labelledColor: LabelledColor?): String {
        return gson.toJson(labelledColor)
    }

    @TypeConverter
    fun toLabelledColor(value: String): LabelledColor? {
        return try {
            gson.fromJson(value, LabelledColor::class.java)
        } catch (e: Exception) {
            null
        }
    }


}