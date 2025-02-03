package com.example.ifataliku.data.repository

import com.example.ifataliku.data.datasource.local.SouvenirLocalDataSource
import com.example.ifataliku.data.datasource.local.entities.Souvenir
import com.example.ifataliku.domain.repository.SouvenirRepo
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class SouvenirRepoImpl @Inject constructor(
    private var localDataSource: SouvenirLocalDataSource
) : SouvenirRepo {

    override suspend fun createSouvenir(souvenir: Souvenir): Boolean {
        return try {
            val newSouvenir = souvenir.copy(id = UUID.randomUUID().toString())
            localDataSource.addSouvenir(newSouvenir)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override  fun getSouvenirById(id: String): Flow<Souvenir?> {
        return localDataSource.getSouvenirById(id)
    }

    override  fun getAllSouvenirs(): Flow<List<Souvenir>> {
        return localDataSource.getAllSouvenirs()
    }

    override suspend fun updateSouvenir(souvenir: Souvenir): Boolean {
        return localDataSource.updateSouvenir(souvenir) >= 1
    }

    override suspend fun deleteSouvenirById(id: String): Boolean {
        return localDataSource.deleteSouvenirById(id) >= 1

    }

    override suspend fun deleteAllSouvenirs(): Boolean {
        return localDataSource.clearAllSouvenirs() >= 1
    }
}