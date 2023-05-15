package com.laad.viniloapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.laad.viniloapp.data.AlbumRepository
import com.laad.viniloapp.data.database.VinylRoomDatabase
import com.laad.viniloapp.models.Album

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).albumsDao()
    )

    private val _albums = MutableLiveData<List<Album>>()

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val albums: LiveData<List<Album>>
        get() = _albums

//    init {
//        consultaAlbum()
//    }

//    private fun consultaAlbum() {
//        albumsRepository.consultaAlbum({
//            _albums.postValue(it)
//        }, {
//            _eventNetworkError.value = true
//        })
//   }

}