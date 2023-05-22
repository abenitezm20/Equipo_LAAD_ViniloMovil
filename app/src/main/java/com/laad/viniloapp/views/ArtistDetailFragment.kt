package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.ArtistDetailFragmentBinding
import com.laad.viniloapp.models.Artist


class ArtistDetailFragment : Fragment() {

    private var _binding: ArtistDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: ArtistDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArtistDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val artist = args.artist
        Log.d("ArtistDetailFragment", "Artist ${artist.name}")
        bindCover(artist)
        binding.detailArtistName.text = artist.name
        binding.detailArtistBday.text = artist.birthDate
        binding.detailArtistDescription.text = artist.description
    }

    private fun bindCover(artist: Artist) {
        Glide.with(this).load(artist.image.toUri().buildUpon().scheme("https").build()).apply(
            RequestOptions().placeholder(R.drawable.loading_image)
                .error(R.drawable.ic_broken_image)
        ).into(binding.artistCover)
    }
}