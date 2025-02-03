package com.example.ifataliku.data.datasource.local

import com.example.ifataliku.data.datasource.local.entities.Souvenir
import kotlinx.coroutines.flow.Flow

interface SouvenirLocalDataSource {
    fun getAllSouvenirs(): Flow<List<Souvenir>>

    // Add a new souvenir
    suspend fun addSouvenir(souvenir: Souvenir): Long

    // Delete a souvenir
    suspend fun deleteSouvenir(souvenir: Souvenir): Int

    // Find a souvenir by id
    fun getSouvenirById(id: String): Flow<Souvenir?>

    // Update a souvenir
    suspend fun updateSouvenir(souvenir: Souvenir): Int

    // delete souvenir by String
    suspend fun deleteSouvenirById(id: String): Int

    // Clear all souvenirs
    suspend fun clearAllSouvenirs() : Int
}