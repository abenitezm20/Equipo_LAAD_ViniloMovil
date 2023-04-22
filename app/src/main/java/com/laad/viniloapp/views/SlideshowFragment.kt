package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.FragmentSlideshowBinding
import com.laad.viniloapp.databinding.ItemPruebaBinding
import com.laad.viniloapp.models.PruebaDTO
import com.laad.viniloapp.viewmodels.GalleryViewModel
import com.laad.viniloapp.viewmodels.SlideshowViewModel
import com.laad.viniloapp.views.adapters.SlideshowAdapter
import java.util.Arrays

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: SlideshowViewModel
    private var viewModelAdapter: SlideshowAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = SlideshowAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.pruebaRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this)[SlideshowViewModel::class.java]
        viewModel.listaTitulos.observe(viewLifecycleOwner, Observer { value ->
            viewModelAdapter!!.items = value
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
