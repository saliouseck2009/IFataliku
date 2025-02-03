package com.example.ifataliku.data.datasource.converter

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)


class StringListConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(users: List<String>?): String {
        return gson.toJson(users)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}


