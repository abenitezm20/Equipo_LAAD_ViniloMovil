package com.laad.viniloapp.data

import android.app.Application
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Album

class AlbumRepository(val application: Application) {

    suspend fun consultaAlbum(): List<Album> {
        return ViniloServiceAdapter.getInstance(application).getAlbums()
    }

}