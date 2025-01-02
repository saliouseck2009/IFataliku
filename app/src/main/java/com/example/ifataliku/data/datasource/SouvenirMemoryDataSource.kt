package com.example.ifataliku.data.datasource

import com.example.ifataliku.domain.entities.Souvenir
import com.example.ifataliku.domain.entities.souvenirs


class SouvenirMemoryDataSource {
    private val souvenirList = souvenirs.toMutableList()


    // Get all souvenirs
    fun getAllSouvenirs(): List<Souvenir> = souvenirList

    // Add a new souvenir
    fun addSouvenir(souvenir: Souvenir) {
        souvenirList.add(souvenir)
    }

    // Delete a souvenir
    fun deleteSouvenir(souvenir: Souvenir) {
        souvenirList.remove(souvenir)
    }

    // Find a souvenir by id
    fun getSouvenirById(id: String): Souvenir? = souvenirList.find { it.id == id }

    // Update a souvenir
    fun updateSouvenir(souvenir: Souvenir): Boolean {
        val index = souvenirList.indexOfFirst { it.id == souvenir.id }
        return if (index != -1) {
            souvenirList[index] = souvenir
            true
        }else {
            false
        }
    }

    // delete souvenir by String
    fun deleteSouvenirById(id: String): Boolean {
        val index = souvenirList.indexOfFirst { it.id == id }
        return if (index != -1) {
            souvenirList.removeAt(index)
            true
        }else {
            false
        }
    }

    // Clear all souvenirs
    fun clearAllSouvenirs() : Boolean {
        return try {
            souvenirList.clear()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}