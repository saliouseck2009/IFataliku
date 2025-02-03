package com.example.ifataliku.data.datasource.local

import com.example.ifataliku.data.datasource.local.entities.Souvenir
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SouvenirRoomDataSource @Inject constructor(
    private val db: AppDatabase
) : SouvenirLocalDataSource {
    override fun getAllSouvenirs() = db.souvenirDao().getAllSouvenirs()


    override suspend fun addSouvenir(souvenir: Souvenir) = db.souvenirDao().insert(souvenir)

    override suspend fun deleteSouvenir(souvenir: Souvenir) = db.souvenirDao().delete(souvenir)

    override fun getSouvenirById(id: String) = db.souvenirDao().getSouvenirById(id)

    override suspend fun updateSouvenir(souvenir: Souvenir) = db.souvenirDao().update(souvenir)

    override suspend fun deleteSouvenirById(id: String)= db.souvenirDao().deleteById(id)

    override suspend fun clearAllSouvenirs()= db.souvenirDao().deleteAll()

}