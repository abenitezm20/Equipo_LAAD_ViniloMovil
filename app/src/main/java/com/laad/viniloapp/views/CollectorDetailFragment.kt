package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.CollectorDetailFragmentBinding
import com.laad.viniloapp.databinding.ListPerformersCollectorBinding
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.models.FavoritePerformers
import com.laad.viniloapp.views.adapters.CollectorPerformersAdapter

class CollectorDetailFragment : Fragment() {

    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: CollectorDetailFragmentArgs by navArgs()
    private lateinit var recyclerView: RecyclerView
    private var viewModelAdapter: CollectorPerformersAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        viewModelAdapter = CollectorPerformersAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.lvPerformersCollector
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val collector = args.collector
        Log.d("CollectorDetailFragment", "Collector ${collector.name}")
        val favoritePerformers: List<FavoritePerformers>? = collector.favoritePerformers
        if (favoritePerformers != null) {
            viewModelAdapter!!.favoritePerformers = favoritePerformers
        }
    }

}
