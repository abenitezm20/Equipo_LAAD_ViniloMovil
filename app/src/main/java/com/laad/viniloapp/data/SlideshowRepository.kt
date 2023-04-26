package com.laad.viniloapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.laad.viniloapp.models.PruebaDTO

class SlideshowRepository {

    private val titulos = MutableLiveData<List<PruebaDTO>>()

    fun getTitulos(): LiveData<List<PruebaDTO>> {
        titulos.value = listOf(
            PruebaDTO("Cien años de soledad"),
            PruebaDTO("Crónica de una muerte anunciada"),
            PruebaDTO("El amor en los tiempos del cólera"),
            PruebaDTO("El coronel no tiene quien le escriba"),
            PruebaDTO("Memoria de mis putas tristes"),
            PruebaDTO("Del amor y otros demonios")
        )
        return titulos
    }
}