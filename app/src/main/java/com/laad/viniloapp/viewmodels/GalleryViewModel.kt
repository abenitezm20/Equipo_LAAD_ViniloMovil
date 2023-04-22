package com.laad.viniloapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.laad.viniloapp.data.GalleryRepository

class GalleryViewModel : ViewModel() {

    private val repository = GalleryRepository()

    val textoLiveData: LiveData<String>
        get() = repository.getHolaMundo()

    fun changeText(text: String): LiveData<String> {
        return repository.changeText(text)
    }

}