package com.laad.viniloapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.laad.viniloapp.data.database.CachedAlbumsDao
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Album

class AlbumRepository(val application: Application, private val cachedAlbumsDao: CachedAlbumsDao) {

    suspend fun consultaAlbum(): List<Album> {
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

}