package com.laad.viniloapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laad.viniloapp.data.ArtistRepository
import com.laad.viniloapp.models.Artist

class ArtistViewModel(application: Application) : AndroidViewModel(application) {
    private val _artist = MutableLiveData<List<Artist>>()
    private val artistRepository = ArtistRepository(application)

    val artists: LiveData<List<Artist>>
        get() = _artist

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
        artistRepository.consultaArtist({
            _artist.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED CAST")
                return ArtistViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}