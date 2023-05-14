package com.laad.viniloapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.laad.viniloapp.models.FavoritePerformers

@Dao
interface FavoritePerformersDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectorPerformer: FavoritePerformers)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectorPerformers: List<FavoritePerformers>)

    @Transaction
    @Query("DELETE FROM cached_collector_performers")
    suspend fun deleteAll(): Int

}