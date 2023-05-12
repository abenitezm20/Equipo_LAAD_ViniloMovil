package com.laad.viniloapp.data

import android.app.Application
import com.android.volley.VolleyError
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Collector

class CollectorRepository (val application: Application) {

    fun consultaCollector(callback: (List<Collector>) -> Unit, onError: (VolleyError) -> Unit) {
        ViniloServiceAdapter.getInstance(application).getCollector({ callback(it)}, onError)
    }
}