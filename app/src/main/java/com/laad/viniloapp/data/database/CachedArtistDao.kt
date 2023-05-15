package com.laad.viniloapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laad.viniloapp.models.Artist

@Dao
interface CachedArtistDao {
    @Query("SELECT * FROM cached_artist")
    fun getArtists(): List<Artist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artist: Artist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artists: List<Artist>)

    @Query("DELETE FROM cached_artist")
    suspend fun deleteAll(): Int
}