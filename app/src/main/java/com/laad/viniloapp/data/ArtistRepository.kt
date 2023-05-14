package com.laad.viniloapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.VolleyError
import com.laad.viniloapp.data.database.CachedAlbumsDao
import com.laad.viniloapp.data.database.CachedArtistDao
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Artist
import com.laad.viniloapp.utilities.CACHE_EXPIRATION_TIME

class ArtistRepository(val application: Application, private val cachedArtistDao: CachedArtistDao) {

    companion object {
        var startTime: Long = System.currentTimeMillis()
    }
    suspend fun consultaArtist(): List<Artist> {
        processExpiredCache()
        var cachedArtist = cachedArtistDao.getArtists()

        return if (cachedArtist.isNullOrEmpty()) {
            if (isDisabledNetwork()) {
                Log.d("ArtistRepository", "No hay conexión a internet ni información en cache")
                emptyList()
            } else {
                Log.d("ArtistRepository", "Información desde API Rest")
                val artistList = ViniloServiceAdapter.getInstance(application).getArtists()
                cachedArtistDao.insert(artistList)
                artistList
            }
        } else {
            Log.d("ArtistRepository", "Información artistas desde cache BD")
            cachedArtist
        }
    }

    private fun isDisabledNetwork(): Boolean {
        val cm =
            application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE
    }

    private suspend fun processExpiredCache() {
        Log.d("ArtistRepository", "Tiempo inicio cache ${startTime}")
        val endTime = System.currentTimeMillis()
        if ((endTime - startTime) > CACHE_EXPIRATION_TIME) {
            Log.d("ArtistRepository", "Venció cache")
            startTime = endTime
            cachedArtistDao.deleteAll()
        }
    }
}