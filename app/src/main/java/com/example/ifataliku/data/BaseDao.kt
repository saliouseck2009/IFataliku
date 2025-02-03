package com.example.ifataliku.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update

interface BaseDao<T> {

    /**
     * Insert all
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(models: List<T>): List<Long>

    /**
     * Insert One
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: T): Long

    @Update
    suspend fun update(model: T): Int

    @Update
    @Transaction
    suspend fun updateAll(models: List<T>): Int
    /**
     * Delete One
     */
    @Delete
    suspend fun delete(model: T): Int
}