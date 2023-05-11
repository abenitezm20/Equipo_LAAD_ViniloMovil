package com.laad.viniloapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.laad.viniloapp.models.Album

@Dao
interface CachedAlbumsDao {

    @Query("SELECT * FROM cached_albums")
    fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albums: List<Album>)

    @Query("DELETE FROM cached_albums")
    suspend fun deleteAll(): Int

}