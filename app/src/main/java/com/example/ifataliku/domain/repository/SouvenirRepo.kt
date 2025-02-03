package com.example.ifataliku.domain.repository

import com.example.ifataliku.data.datasource.local.entities.Souvenir
import kotlinx.coroutines.flow.Flow

interface SouvenirRepo {
    suspend fun createSouvenir(souvenir: Souvenir): Boolean
     fun getSouvenirById(id: String): Flow<Souvenir?>
     fun getAllSouvenirs(): Flow<List<Souvenir>>
    suspend fun updateSouvenir(souvenir: Souvenir): Boolean
    suspend fun deleteSouvenirById(id: String): Boolean
    suspend fun deleteAllSouvenirs(): Boolean
}