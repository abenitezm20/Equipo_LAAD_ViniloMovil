package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.FragmentHomeBinding
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.viewmodels.HomeViewModel
import com.laad.viniloapp.viewmodels.SlideshowViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Obtener el TextView del layout
        val stringTextHome = view?.findViewById<TextView>(R.id.text_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.albums.observe(viewLifecycleOwner) {
            Log.d("HomeFragment", it.toString())
            if (stringTextHome != null) {
                stringTextHome.text = it.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}