package com.example.ifataliku.data.repository

import com.example.ifataliku.data.datasource.SouvenirMemoryDataSource
import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs
import com.example.ifataliku.domain.repository.SouvenirRepo
import java.util.UUID
import javax.inject.Inject

//private val souvenirList = emptyList<Souvenir>().toMutableList()
class SouvenirRepoImpl @Inject constructor(
    private var souvenirMemoryDataSource: SouvenirMemoryDataSource
) : SouvenirRepo {
    // souvenirs.toMutableList()

    override suspend fun createSouvenir(souvenir: Souvenir): Boolean {
        return try {
            val newSouvenir = souvenir.copy(id = UUID.randomUUID().toString())
            souvenirMemoryDataSource.addSouvenir(newSouvenir)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getSouvenirById(id: String): Souvenir? {
        return souvenirMemoryDataSource.getSouvenirById(id)
    }

    override suspend fun getAllSouvenirs(): List<Souvenir> {
        return souvenirMemoryDataSource.getAllSouvenirs()
    }

    override suspend fun updateSouvenir(souvenir: Souvenir): Boolean {
        return souvenirMemoryDataSource.updateSouvenir(souvenir)
    }

    override suspend fun deleteSouvenirById(id: String): Boolean {
        return souvenirMemoryDataSource.deleteSouvenirById(id)

    }

    override suspend fun deleteAllSouvenirs(): Boolean {
        return souvenirMemoryDataSource.clearAllSouvenirs()
    }
}