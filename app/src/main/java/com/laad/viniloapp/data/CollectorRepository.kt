package com.laad.viniloapp.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.laad.viniloapp.data.database.CachedCollectorDao
import com.laad.viniloapp.data.database.FavoritePerformersDao
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.CollectorPerformers
import com.laad.viniloapp.utilities.CACHE_EXPIRATION_TIME

class CollectorRepository(
    val application: Application,
    private val cachedCollectorDao: CachedCollectorDao,
    private val favoritePerformersDao: FavoritePerformersDao
) {

    companion object {
        var startTime: Long = System.currentTimeMillis()
    }

    suspend fun consultaCollector(): List<CollectorPerformers> {
        processExpiredCache()
        var cachedCollectorsPerformers = cachedCollectorDao.getCollectorPerformers()

        return if (cachedCollectorsPerformers.isNullOrEmpty()) {
            if (isDisabledNetwork()) {
                Log.d("CollectorRepository", "No hay conexión a internet ni información en cache")
                emptyList()
            } else {
                Log.d("CollectorRepository", "Información desde API Rest")
                val apiResponse = ViniloServiceAdapter.getInstance(application).getCollector()
                insertCollectorPerformers(apiResponse)
                apiResponse
            }
        } else {
            Log.d("CollectorRepository", "Información coleccionistas desde cache BD")
            cachedCollectorsPerformers
        }
    }

    private suspend fun insertCollectorPerformers(
        collectorPerformers: List<CollectorPerformers>
    ) {
        for (i in collectorPerformers.indices) {
            Log.d(
                "CollectorRepository",
                "Coleccionista ${collectorPerformers[i].collector.id}"
            )
            cachedCollectorDao.insert(collectorPerformers[i].collector)

            for (x in collectorPerformers[i].favoritePerformers.indices) {
                collectorPerformers[i].favoritePerformers[x].collectorId =
                    collectorPerformers[i].collector.id
                favoritePerformersDao.insert(collectorPerformers[i].favoritePerformers[x])
                Log.d(
                    "CollectorRepository",
                    "Artista favorito ${collectorPerformers[i].favoritePerformers[x].id_favorite_performers}"
                )
            }
        }
    }

    private fun isDisabledNetwork(): Boolean {
        val cm =
            application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE
    }

    private suspend fun processExpiredCache() {
        Log.d("CollectorRepository", "Tiempo inicio cache ${ArtistRepository.startTime}")
        val endTime = System.currentTimeMillis()
        if ((endTime - startTime) > CACHE_EXPIRATION_TIME) {
            Log.d("CollectorRepository", "Venció cache")
            startTime = endTime
            favoritePerformersDao.deleteAll()
            cachedCollectorDao.deleteAll()
        }
    }

}