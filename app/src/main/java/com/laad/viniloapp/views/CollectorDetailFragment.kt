package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.databinding.CollectorDetailFragmentBinding
import com.laad.viniloapp.models.FavoritePerformers
import com.laad.viniloapp.views.adapters.CollectorPerformersAdapter

class CollectorDetailFragment: Fragment() {

    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: CollectorDetailFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    private var viewModelAdapter: CollectorPerformersAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.lvPerformersCollector
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
        val collector = args.collector
        Log.d("CollectorDetailFragment", "Collector ${collector.name}")
        val favoritePerformers: List<FavoritePerformers>? = collector.favoritePerformers
        if (favoritePerformers != null) {
            viewModelAdapter?.favoritePerformers = favoritePerformers
        }
    }
}
    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val collector = args.collector
        Log.d("CollectorDetailFragment", "Album ${collector.name}")
        val favoritePerformers:List<FavoritePerformers>?=collector.favoritePerformers
        Log.d("CollectorDetailFragment", "Collector ${favoritePerformers}")
        recyclerView = binding.lvPerformersCollector
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

*/


        /*
        bindCover(collector)

        binding.detailAlbumName.text = album.name
        binding.detailAlbumRelease.text =
            getString(R.string.album_release, Utils.formatDate(album.releaseDate))
        binding.detailAlbumGenre.text = getString(R.string.album_genre, album.genre)
        binding.detailAlbumRecordLabel.text =
            getString(R.string.album_record_label, album.recordLabel)
        binding.detailAlbumDescription.text = album.description

         */


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Controla para que cuando se le de atras retorne al mainActivity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_collectorFragment_to_mainActivity);
        }
    }*/







