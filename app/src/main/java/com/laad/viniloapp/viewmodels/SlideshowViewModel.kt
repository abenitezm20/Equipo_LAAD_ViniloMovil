package com.laad.viniloapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laad.viniloapp.data.GalleryRepository
import com.laad.viniloapp.data.SlideshowRepository
import com.laad.viniloapp.models.PruebaDTO

class SlideshowViewModel : ViewModel() {

    private val repository = SlideshowRepository()
    private val titulos = MutableLiveData<List<PruebaDTO>>()

    val listaTitulos: LiveData<List<PruebaDTO>>
        get() = repository.getTitulos()


}