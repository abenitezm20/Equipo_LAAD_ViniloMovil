package com.laad.viniloapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.laad.viniloapp.R
import com.laad.viniloapp.viewmodels.GalleryViewModel

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    private lateinit var stringTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inicializar el ViewModel
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        // Inflar la vista del Fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        // Obtener el TextView del layout
        val stringTextView = view.findViewById<TextView>(R.id.text_gallery)

        // Obtener referencia del botón y agregar listener para el evento onClick
        val galleryButton = view.findViewById<Button>(R.id.action_text_button)
        galleryButton.setOnClickListener {
            galleryViewModel.changeText(stringTextView.text as String)
        }

        // Observar los cambios del valor del String y actualizar el TextView
        galleryViewModel.textoLiveData.observe(viewLifecycleOwner) { nuevoTexto ->
            stringTextView.text = nuevoTexto
        }

        return view
    }
}