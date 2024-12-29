package com.example.ifataliku.home.reflection

data class Category(
    val emoji : String,
    val title : String,
){
    override fun toString(): String {
        return "$emoji $title"
    }
}

