package com.laad.viniloapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.models.Artist
import com.laad.viniloapp.models.Collector
import com.laad.viniloapp.models.FavoritePerformers

@Database(
    entities = [Album::class, Artist::class, Collector::class, FavoritePerformers::class],
    version = 1,
    exportSchema = false
)
abstract class VinylRoomDatabase : RoomDatabase() {

    abstract fun albumsDao(): CachedAlbumsDao
    abstract fun artistDao(): CachedArtistDao
    abstract fun collectorDao(): CachedCollectorDao
    abstract fun FavoritePerformersDao(): FavoritePerformersDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
