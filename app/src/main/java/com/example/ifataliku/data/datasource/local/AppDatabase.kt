package com.example.ifataliku.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ifataliku.data.datasource.LabelledColorConverter
import com.example.ifataliku.data.datasource.converter.StringListConverter
import com.example.ifataliku.data.datasource.local.dao.SouvenirDao
import com.example.ifataliku.data.datasource.local.entities.CoordinateConverter
import com.example.ifataliku.data.datasource.local.entities.Souvenir
import com.example.ifataliku.home.reflection.CategoryConverter

@Database(
    entities = [
       Souvenir::class
    ],
    version = 1
)
@TypeConverters(
    StringListConverter::class,
    CategoryConverter::class,
    CoordinateConverter::class,
    LabelledColorConverter::class,
    )
abstract class AppDatabase: RoomDatabase() {
    abstract fun souvenirDao(): SouvenirDao

}