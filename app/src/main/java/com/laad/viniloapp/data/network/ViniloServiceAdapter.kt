package com.laad.viniloapp.data.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.models.AlbumRequest
import com.laad.viniloapp.models.Artist
import com.laad.viniloapp.models.Collector
import com.laad.viniloapp.models.CollectorPerformers
import com.laad.viniloapp.models.Comment
import com.laad.viniloapp.models.CommentRequest
import com.laad.viniloapp.models.FavoritePerformers
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { cont ->
        Log.d("ViniloServiceAdapter", "Consultando albumes")
        requestQueue.add(
            getRequest("albums", { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, extractAlbum(item))
                }
                Log.d("ViniloServiceAdapter", list.size.toString() + " albumes consultados")
                cont.resume(list)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun createAlbum(album: AlbumRequest) = suspendCoroutine<Album> { cont ->
        Log.d("ViniloServiceAdapter", "Creando album")
        requestQueue.add(
            postRequest("albums", album, { response ->
                cont.resume(extractAlbum(response))
            }, { cont.resumeWithException(it) })
        )
    }

    private fun extractAlbum(item: JSONObject): Album {
        return Album(
            id = item.getInt("id"),
            name = item.getString("name"),
            cover = item.getString("cover"),
            recordLabel = item.getString("recordLabel"),
            releaseDate = item.getString("releaseDate"),
            genre = item.getString("genre"),
            description = item.getString("description")
        )
    }

    suspend fun createComment(albumid: Int, comment: CommentRequest) = suspendCoroutine<Comment> { cont ->
        Log.d("ViniloServiceAdapter", "Creando Commentario adapter")
        requestQueue.add(
            postRequest("albums/"+albumid+ "/comments", comment, { response ->
                var strResp = response.toString()
                Log.d("API", strResp)
                cont.resume(extractComment(albumid, response))
            }, {
                Response.ErrorListener {Log.d("API", "that didn't work") }
                cont.resumeWithException(it)
            })
        )
    }

    private fun extractComment(albumid: Int, item: JSONObject): Comment {
        return Comment(
            id = item.getInt("id"),
            description = item.getString("description"),
            rating = Integer.parseInt(item.getString("rating")),
            albumId = albumid,
            collectorId = 1
        )
    }

    suspend fun getArtists() = suspendCoroutine<List<Artist>> { cont ->
        Log.d("ViniloServiceAdapter", "Consultando artistas")
        val dOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val dFormat = SimpleDateFormat("yyyy-MM-dd")
        requestQueue.add(
            getRequest("musicians", { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()


                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)

                    var birthD: String = ""
                    var createD: String = ""

                    if (item.has("birthDate")) birthD =
                        dFormat.format(dOriginal.parse(item.getString("birthDate")))
                    if (item.has("creationDate")) createD =
                        dFormat.format(dOriginal.parse(item.getString("creationDate")))

                    list.add(
                        i, Artist(
                            id = item.getInt("id"),
                            name = "Nombre: " + item.getString("name"),
                            image = item.getString("image"),
                            description = item.getString("description"),
                            birthDate = birthD,
                            creationDate = createD
                        )
                    )
                }
                Log.d("ViniloServiceAdapter", list.size.toString() + " artistas consultados")
                cont.resume(list)
            }, { cont.resumeWithException(it) })
        )
    }

    suspend fun getCollector() = suspendCoroutine<List<CollectorPerformers>> { cont ->
        Log.d("ViniloServiceAdapter", "Consultando coleccionistas")

        requestQueue.add(
            getRequest("collectors", { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<CollectorPerformers>()


                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)

                    list.add(
                        i, CollectorPerformers(
                            Collector(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                telephone = item.getString("telephone"),
                                email = item.getString("email"),
                            ),
                            getPerformers(item.getInt("id"), item.getString("favoritePerformers"))
                        )
                    )
                }
                cont.resume(list)
            }, { cont.resumeWithException(it) })
        )

    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun <T> postRequest(
        path: String,
        data: T,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        val jsonString = Gson().toJson(data)
        Log.d("postRequest", jsonString)
        val body = JSONObject(jsonString)
        return JsonObjectRequest(
            Request.Method.POST, BASE_URL + path, body, responseListener, errorListener
        )
    }

    private fun getPerformers(collectorId: Int, jsonPerformers: String): List<FavoritePerformers> {
        val datos = JSONArray(jsonPerformers)
        val list = mutableListOf<FavoritePerformers>()

        for (i in 0 until datos.length()) {
            val item = datos.getJSONObject(i)

            list.add(
                i, FavoritePerformers(
                    id_favorite_performers = item.getInt("id"),
                    name = item.getString("name"),
                    image = item.getString("image"),
                    description = item.getString("description"),
                    collectorId = collectorId
                )
            )
        }
        return list
    }

}