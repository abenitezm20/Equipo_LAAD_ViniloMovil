package com.laad.viniloapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.laad.viniloapp.data.database.CachedAlbumsDao
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.utilities.CACHE_EXPIRATION_TIME

class AlbumRepository(val application: Application, private val cachedAlbumsDao: CachedAlbumsDao) {

    companion object {
        var startTime: Long = System.currentTimeMillis()
    }

    suspend fun consultaAlbum(): List<Album> {

        processExpiredCache()
        var cachedAlbums = cachedAlbumsDao.getAlbums()

        return if (cachedAlbums.isNullOrEmpty()) {
            if (isDisabledNetwork()) {
                Log.d("AlbumRepository", "No hay conexión a internet ni información en cache")
                emptyList()
            } else {
                Log.d("AlbumRepository", "Información desde API Rest")
                val albumList = ViniloServiceAdapter.getInstance(application).getAlbums()
                cachedAlbumsDao.insert(albumList)
                albumList
            }
        } else {
            Log.d("AlbumRepository", "Información albumes desde cache BD")
            cachedAlbums
        }
    }

    private fun isDisabledNetwork(): Boolean {
        val cm =
            application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE
    }

    private suspend fun processExpiredCache() {
        Log.d("AlbumRepository", "Tiempo inicio cache $startTime")
        val endTime = System.currentTimeMillis()
        if ((endTime - startTime) > CACHE_EXPIRATION_TIME) {
            Log.d("AlbumRepository", "Venció cache")
            startTime = endTime
            cachedAlbumsDao.deleteAll()
        }
    }

}