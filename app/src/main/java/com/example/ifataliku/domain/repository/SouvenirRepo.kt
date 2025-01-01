package com.example.ifataliku.domain.repository

import com.example.ifataliku.domain.entities.Souvenir

interface SouvenirRepo {
    suspend fun createSouvenir(souvenir: Souvenir): Boolean
    suspend fun getSouvenirById(id: String): Souvenir?
    suspend fun getAllSouvenirs(): List<Souvenir>
    suspend fun updateSouvenir(souvenir: Souvenir): Boolean
    suspend fun deleteSouvenirById(id: String): Boolean
    suspend fun deleteAllSouvenirs(): Boolean
}