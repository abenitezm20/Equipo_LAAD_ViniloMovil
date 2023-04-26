package com.laad.viniloapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GalleryRepository {

    private val text = MutableLiveData<String>()

    fun getHolaMundo(): LiveData<String> {
        text.value = "Hola"
        return text
    }

    fun changeText(newText: String): LiveData<String> {
        if ("Hola".equals(newText)) {
            text.value = "Hola :)"
        } else {
            text.value = "Hola"
        }
        return text
    }

}