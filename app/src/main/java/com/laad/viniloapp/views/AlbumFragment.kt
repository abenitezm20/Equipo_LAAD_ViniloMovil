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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.laad.viniloapp.R
import com.laad.viniloapp.ViniloApp
import com.laad.viniloapp.databinding.AlbumFragmentBinding
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.utilities.ALBUM_CREATED
import com.laad.viniloapp.utilities.AppRole
import com.laad.viniloapp.utilities.Utils
import com.laad.viniloapp.viewmodels.AlbumViewModel
import com.laad.viniloapp.views.adapters.AlbumAdapter
import java.util.Objects


class AlbumFragment : Fragment() {

    companion object {
        var onCreate: Boolean = false
    }

    private var _binding: AlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d("AlbumFragment", "onCreateView")
        _binding = AlbumFragmentBinding.inflate(inflater, container, false)
        viewModelAdapter = AlbumAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("AlbumFragment", "onViewCreated")
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        val rol: String = ViniloApp.rol?.value ?: AppRole.VISITOR.value
        if (AppRole.COLLECTOR.value == rol) {
            fab.show()
            fab.setOnClickListener { view ->
                findNavController().navigate(R.id.nav_create_album)
            }
        } else {
            fab.hide()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("AlbumFragment", "onActivityCreated")
        val activity = requireNotNull(this.activity)
        viewModel = AlbumViewModel.getInstance(activity.application)
        viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
                Log.d("AlbumFragment", "Cambio en lista albumes " + viewModelAdapter!!.itemCount)
                Log.d("AlbumFragment", Objects.toString(this))
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

        if (viewModel.isCreateAlbumError.value == ALBUM_CREATED) {
            Log.d("AlbumFragment", "Album ya creado")
            processNewAlbum()
        }

        viewModel.isCreateAlbumError.observe(viewLifecycleOwner) { code ->
            when (code) {
                ALBUM_CREATED -> {
                    Log.d("AlbumFragment", "Album recien creado")
                    processNewAlbum()
                }
            }
        }
    }

    private fun processNewAlbum() {
        viewModel.refreshDataFromNetwork()
        viewModel.resetCreateAlbumFlag()
        context?.let { Utils.showToast(it, "{Álbum creado}") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("AlbumFragment", "onDestroyView")
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
        Log.d("AlbumFragment", "onCreate")
        onCreate = true
        //Controla para que cuando se le de atras retorne al mainActivity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_nav_albums_to_mainActivity);
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("AlbumFragment", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("AlbumFragment", "onResume")
        if (onCreate) {
            onCreate = false
        } else {
            viewModel.refreshDataFromNetwork()
        }
    }
}