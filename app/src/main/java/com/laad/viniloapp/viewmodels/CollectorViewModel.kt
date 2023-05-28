package com.laad.viniloapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.laad.viniloapp.data.CollectorRepository
import com.laad.viniloapp.data.database.VinylRoomDatabase
import com.laad.viniloapp.models.CollectorPerformers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorViewModel(application: Application) : AndroidViewModel(application) {
    private val _collector = MutableLiveData<List<CollectorPerformers>>()
    private val collectorRepository = CollectorRepository(
        application,
        VinylRoomDatabase.getDatabase(application.applicationContext).collectorDao(),
        VinylRoomDatabase.getDatabase(application.applicationContext).FavoritePerformersDao(),
    )

    val collectors: LiveData<List<CollectorPerformers>>
        get() = _collector

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
                    val data = collectorRepository.consultaCollector()
                    _collector.postValue(data)
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

    class Factory(val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED CAST")
                return CollectorViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }

}