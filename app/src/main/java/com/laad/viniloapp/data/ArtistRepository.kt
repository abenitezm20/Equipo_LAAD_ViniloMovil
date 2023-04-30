package com.laad.viniloapp.data

import android.app.Application
import com.android.volley.VolleyError
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Artist

class ArtistRepository(val application: Application) {

    fun consultaArtist(callback: (List<Artist>) -> Unit, onError: (VolleyError) -> Unit) {
        ViniloServiceAdapter.getInstance(application).getArtists({ callback(it)}, onError)
    }
}