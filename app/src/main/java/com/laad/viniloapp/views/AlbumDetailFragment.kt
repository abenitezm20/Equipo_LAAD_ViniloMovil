package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.FragmentAlbumDetailBinding
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.utilities.Utils

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AlbumDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val album = args.album
        Log.d("AlbumDetailFragment", "Album ${album.name}")
        bindCover(album)
        binding.detailAlbumName.text = album.name
        binding.detailAlbumRelease.text =
            getString(R.string.album_release) + Utils.formatDate(album.releaseDate)
        binding.detailAlbumGenre.text = getString(R.string.album_genre) + album.genre
        binding.detailAlbumRecordLabel.text =
            getString(R.string.album_record_label) + album.recordLabel
        binding.detailAlbumDescription.text = album.description

    }

    fun bindCover(album: Album) {
        Glide.with(this)
            .load(album.cover.toUri().buildUpon().scheme("https").build())
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.ic_broken_image)
            )
            .into(binding.albumCover)
    }

}