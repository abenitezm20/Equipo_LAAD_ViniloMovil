package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.laad.viniloapp.R
import com.laad.viniloapp.ViniloApp
import com.laad.viniloapp.databinding.FragmentAlbumDetailBinding
import com.laad.viniloapp.models.Album
import com.laad.viniloapp.utilities.AppRole
import com.laad.viniloapp.utilities.Utils
import com.laad.viniloapp.viewmodels.CommentViewModel

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AlbumDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
            getString(R.string.album_release, Utils.formatDate(album.releaseDate))
        binding.detailAlbumGenre.text = getString(R.string.album_genre, album.genre)
        binding.detailAlbumRecordLabel.text =
            getString(R.string.album_record_label, album.recordLabel)
        binding.detailAlbumDescription.text = album.description

        setCommentButton(view, album.id)
    }

    private fun bindCover(album: Album) {
        Glide.with(this).load(album.cover.toUri().buildUpon().scheme("https").build()).apply(
            RequestOptions().placeholder(R.drawable.loading_image)
                .error(R.drawable.ic_broken_image)
        ).into(binding.albumCover)
    }

    private fun setCommentButton(view: View, albumId: Int) {
        val commentAlbumButton: Button = view.findViewById(R.id.comment_album_button)
        val rol: String = ViniloApp.rol?.value ?: AppRole.VISITOR.value
        if (AppRole.COLLECTOR.value == rol) {
            commentAlbumButton.setOnClickListener(View.OnClickListener {
                //enviando parametros
                Log.d("setCommentButton", "Enviando parametro albumId:" + albumId.toString())
                setFragmentResult("requestKey", bundleOf("albumId" to albumId.toString()))
                findNavController().navigate(R.id.nav_comment_album)
            })
        }
        else {
            commentAlbumButton?.isEnabled = false
            commentAlbumButton?.isClickable = false
            commentAlbumButton?.setTextColor(ContextCompat.getColor(view.context, R.color.Fondo))
            commentAlbumButton?.setBackgroundColor(ContextCompat.getColor(view.context, R.color.Fondo))
        }
    }

}