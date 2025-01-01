package com.example.ifataliku.data.datasource

import com.example.ifataliku.domain.entities.Souvenir


class SouvenirMemoryDataSource {
    private val souvenirs = mutableListOf<Souvenir>()
//    private val souvenirs = mutableListOf(
//        Souvenir(
//            emoji = "🎉",
//            title = "Birthday Celebration with Family",
//            date = "2024-01-01",
//            time = "08:00 PM",
//            category = "Celebration",
//            color = "FFD700",
//            feeling = "Happy",
//            images = listOf("image1.jpg", "image2.jpg")
//        ),
//        Souvenir(
//            emoji = "🌅",
//            title = "Mesmerizing Sunrise at the Beach",
//            date = "2024-01-15",
//            time = "06:00 AM",
//            category = "Nature",
//            color = "FFA500",
//            feeling = "Peaceful",
//            images = listOf("sunrise1.jpg", "sunrise2.jpg")
//        ),
//        Souvenir(
//            emoji = "🌌",
//            title = "Stargazing Night with Friends",
//            date = "2024-02-10",
//            time = "10:30 PM",
//            category = "Adventure",
//            color = "800080",
//            feeling = "Amazed",
//            images = listOf("stars1.jpg", "stars2.jpg")
//        ),
//        Souvenir(
//            emoji = "🍕",
//            title = "Pizza Night with Colleagues",
//            date = "2024-02-20",
//            time = "07:00 PM",
//            category = "Food",
//            color = "FF4500",
//            feeling = "Content",
//            images = listOf("pizza1.jpg")
//        ),
//        Souvenir(
//            emoji = "🏞️",
//            title = "Hiking Trip to the Mountains",
//            date = "2024-03-05",
//            time = "09:00 AM",
//            category = "Nature",
//            color = "228B22",
//            feeling = "Excited",
//            images = listOf("hiking1.jpg", "hiking2.jpg", "hiking3.jpg")
//        )
//    )

    // Get all souvenirs
    fun getAllSouvenirs(): List<Souvenir> = souvenirs

    // Add a new souvenir
    fun addSouvenir(souvenir: Souvenir) {
        souvenirs.add(souvenir)
    }

    // Delete a souvenir
    fun deleteSouvenir(souvenir: Souvenir) {
        souvenirs.remove(souvenir)
    }

    // Find a souvenir by id
    fun getSouvenirById(id: String): Souvenir? = souvenirs.find { it.id == id }

    // Update a souvenir
    fun updateSouvenir(souvenir: Souvenir): Boolean {
        val index = souvenirs.indexOfFirst { it.id == souvenir.id }
        return if (index != -1) {
            souvenirs[index] = souvenir
            true
        }else {
            false
        }
    }

    // delete souvenir by String
    fun deleteSouvenirById(id: String): Boolean {
        val index = souvenirs.indexOfFirst { it.id == id }
        return if (index != -1) {
            souvenirs.removeAt(index)
            true
        }else {
            false
        }
    }

    // Clear all souvenirs
    fun clearAllSouvenirs() : Boolean {
        return try {
            souvenirs.clear()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}