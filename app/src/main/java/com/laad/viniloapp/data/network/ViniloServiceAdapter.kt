package com.laad.viniloapp.data.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.models.Artist
import org.json.JSONArray

class ViniloServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL = "https://sgt-backvinyls.herokuapp.com/"
        var instance: ViniloServiceAdapter? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: ViniloServiceAdapter(context).also {
                instance = it
            }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest("albums", { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(
                        i, Album(
                            id = item.getInt("id"),
                            name = item.getString("name"),
                            cover = item.getString("cover"),
                            recordLabel = item.getString("recordLabel"),
                            releaseDate = item.getString("releaseDate"),
                            genre = item.getString("genre"),
                            description = item.getString("description")
                        )
                    )
                }
                onComplete(list)
            }, {
                onError(it)
            })
        )
    }

    fun getArtists(onComplete: (resp: List<Artist>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest("musicians", {response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()

                for (i in 0 until resp.length()) {
                    var item = resp.getJSONObject(i)
                    list.add(
                        i, Artist(
                            name = item.getString("name"),
                            image = item.getString("image"),
                            birthDate = item.getString("birthDate")
                        )
                    )
                }
                onComplete(list)
            }, {onError(it)})
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

}