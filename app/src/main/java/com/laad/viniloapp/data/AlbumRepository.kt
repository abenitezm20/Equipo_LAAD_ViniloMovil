package com.laad.viniloapp.data

import android.app.Application
import com.android.volley.VolleyError
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Album

class AlbumRepository(val application: Application) {

    fun consultaAlbum(callback: (List<Album>) -> Unit, onError: (VolleyError) -> Unit) {
        ViniloServiceAdapter.getInstance(application).getAlbums(
            {
                callback(it)
            }, onError
        )
    }

}