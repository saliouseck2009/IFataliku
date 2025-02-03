package com.example.ifataliku.home.reflection

import androidx.room.TypeConverter
import com.google.gson.Gson

data class Category(
    val emoji : String,
    val title : String,
){
    override fun toString(): String {
        return "$emoji $title"
    }
}

class CategoryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCategory(category: Category?): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun toCategory(value: String): Category? {
        return try {
            gson.fromJson(value, Category::class.java)
        } catch (e: Exception) {
            null
        }
    }
}