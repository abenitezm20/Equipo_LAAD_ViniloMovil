package com.laad.viniloapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.laad.viniloapp.data.AlbumRepository
import com.laad.viniloapp.data.database.VinylRoomDatabase
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.utilities.ALBUM_CREATED
import com.laad.viniloapp.utilities.ALBUM_ERROR
import com.laad.viniloapp.utilities.CREATING_ALBUM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class AlbumViewModel(application: Application) : AndroidViewModel(application), Serializable {

    companion object {
        @Volatile
        private var instance: AlbumViewModel? = null

        fun getInstance(application: Application): AlbumViewModel {
            Log.d("AlbumViewModel", "Obteniendo instancia")
            return instance ?: synchronized(this) {
                instance ?: AlbumViewModel(application).also { instance = it }
            }
        }
    }

    private val _albums = MutableLiveData<List<Album>>()
    private val albumRepository = AlbumRepository(
        application, VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao()
    )

    val albums: LiveData<List<Album>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private var _isCreateAlbumError = MutableLiveData<Int>(CREATING_ALBUM)
    val isCreateAlbumError: LiveData<Int>
        get() = _isCreateAlbumError

    init {
        refreshDataFromNetwork()
    }

    fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    val data = albumRepository.consultaAlbum()
                    _albums.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun createAlbum(
        name: String,
        imageUrl: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String
    ) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    val createdAlbum = albumRepository.createAlbum(
                        Album(
                            id = 0,
                            name = name,
                            cover = imageUrl,
                            releaseDate = releaseDate,
                            description = description,
                            genre = genre,
                            recordLabel = recordLabel
                        )
                    )
                    Log.d("AlbumViewModel", "Nuevo album " + createdAlbum.name)
                    val updated = albums.value.orEmpty().toMutableList()
                    updated.add(createdAlbum)
                    _isCreateAlbumError.postValue(ALBUM_CREATED)
                }
            }
        } catch (e: Exception) {
            _isCreateAlbumError.value = ALBUM_ERROR
        }
    }

    fun resetCreateAlbumFlag() {
        _isCreateAlbumError.value = CREATING_ALBUM
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}