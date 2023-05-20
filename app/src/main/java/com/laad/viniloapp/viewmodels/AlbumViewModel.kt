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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class AlbumViewModel(application: Application) : AndroidViewModel(application), Serializable {
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

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
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
                    Log.d("Aqui", "Nuevo album " + createdAlbum.name)
                    val updatedAlbumsList = _albums.value.orEmpty().toMutableList()
                    updatedAlbumsList.add(createdAlbum)
                    _albums.postValue(updatedAlbumsList)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }

        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
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