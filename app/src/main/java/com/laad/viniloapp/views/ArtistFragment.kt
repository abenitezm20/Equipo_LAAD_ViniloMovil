package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.ArtistFragmentBinding
import com.laad.viniloapp.models.Artist
import com.laad.viniloapp.viewmodels.ArtistViewModel
import com.laad.viniloapp.views.adapters.ArtistAdapter


class ArtistFragment : Fragment() {
    private var _binding: ArtistFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ArtistViewModel
    private var viewModelAdapter: ArtistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArtistFragmentBinding.inflate(inflater, container, false)
        viewModelAdapter = ArtistAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ArtistFragment", "onViewCreated")
        recyclerView = binding.artistRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {}
        viewModel = ViewModelProvider(
            this, ArtistViewModel.Factory(activity.application)
        )[ArtistViewModel::class.java]
        viewModel.artists.observe(viewLifecycleOwner, Observer<List<Artist>> {
            it.apply {
                viewModelAdapter!!.artists = this
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ArtistFragment", "onDestroyView")
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ArtistFragment", "onCreate")
        //Controla para que cuando se le de atras retorne al mainActivity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_nav_artist_to_mainActivity);
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("ArtistFragment", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ArtistFragment", "onResume")
    }

}