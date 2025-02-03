package com.example.ifataliku.data.datasource.local

import com.example.ifataliku.data.datasource.local.entities.Souvenir
import com.example.ifataliku.data.datasource.local.entities.souvenirs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


class SouvenirInMemoryDataSource: SouvenirLocalDataSource {
    //private val souvenirList = souvenirs
    private val souvenirList = souvenirs.toMutableList()


    // Get all souvenirs
    override fun getAllSouvenirs(): Flow<List<Souvenir>> = flowOf( souvenirList)

    // Add a new souvenir
    override suspend fun addSouvenir(souvenir: Souvenir) : Long {
        souvenirList.add(souvenir)
        return 1
    }

    // Delete a souvenir
    override suspend fun deleteSouvenir(souvenir: Souvenir) :Int{
        souvenirList.remove(souvenir)
        return 1
    }

    // Find a souvenir by id
    override  fun getSouvenirById(id: String) = flow { emit(souvenirList
        .find { it.id == id })
    }

    // Update a souvenir
    override suspend fun updateSouvenir(souvenir: Souvenir): Int {
        val index = souvenirList.indexOfFirst { it.id == souvenir.id }
        return if (index != -1) {
            souvenirList[index] = souvenir
            1
        }else {
            0
        }
    }

    // delete souvenir by String
    override suspend fun deleteSouvenirById(id: String): Int {
        val index = souvenirList.indexOfFirst { it.id == id }
        return if (index != -1) {
            souvenirList.removeAt(index)
            1
        }else {
            0
        }
    }

    // Clear all souvenirs
    override suspend fun clearAllSouvenirs() : Int {
        return try {
            souvenirList.clear()
            souvenirList.size
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}