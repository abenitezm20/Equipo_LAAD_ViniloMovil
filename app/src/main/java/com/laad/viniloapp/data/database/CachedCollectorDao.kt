package com.laad.viniloapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.laad.viniloapp.models.Collector
import com.laad.viniloapp.models.CollectorPerformers

@Dao
interface CachedCollectorDao {

    @Transaction
    @Query("SELECT * FROM cached_collector")
    fun getCollectors(): List<Collector>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collector: Collector)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectors: List<Collector>)

    @Transaction
    @Query("DELETE FROM cached_collector")
    suspend fun deleteAll(): Int

    @Transaction
    @Query("SELECT * FROM cached_collector")
    fun getCollectorPerformers(): List<CollectorPerformers>

}