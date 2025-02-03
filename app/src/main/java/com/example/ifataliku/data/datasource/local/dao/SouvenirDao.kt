package com.example.ifataliku.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.ifataliku.data.BaseDao
import com.example.ifataliku.data.datasource.local.entities.Souvenir
import kotlinx.coroutines.flow.Flow

@Dao
interface SouvenirDao: BaseDao<Souvenir> {

    companion object {
        private const val TABLE_NAME = "souvenirs"
        private const val QUERY_GET_ALL = "SELECT * FROM $TABLE_NAME"
        private const val QUERY_GET_BY_ID = "SELECT * FROM $TABLE_NAME WHERE id = :id"
        private const val QUERY_DELETE_BY_ID = "DELETE FROM $TABLE_NAME WHERE id = :id"
        private const val QUERY_DELETE_ALL = "DELETE FROM $TABLE_NAME"
        private const val QUERY_GET_ALL_PAGINATED = "SELECT * FROM $TABLE_NAME LIMIT :limit OFFSET :offset"
    }

    @Query(QUERY_GET_ALL)
    fun getAllSouvenirs(): Flow<List<Souvenir>>

    @Query(QUERY_GET_BY_ID)
    fun getSouvenirById(id: String): Flow<Souvenir?>

    @Query(QUERY_DELETE_BY_ID)
    suspend fun deleteById(id: String): Int

    @Transaction
    @Query(QUERY_DELETE_ALL)
    suspend fun deleteAll(): Int

    @Query(QUERY_GET_ALL_PAGINATED)
    fun getSouvenirsPaginated(limit: Int, offset: Int): Flow<List<Souvenir>>

}