package com.laad.viniloapp.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.ArtistItemBinding
import com.laad.viniloapp.models.Artist
import com.laad.viniloapp.views.AlbumFragmentDirections
import com.laad.viniloapp.views.ArtistFragmentDirections

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    var artists :List<Artist> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistViewHolder {
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false)
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistAdapter.ArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artist = artists[position]
            Log.d("Debug onBindViewHolder", artists[position].toString())
        }
        holder.viewDataBinding.root.setOnClickListener {
            Log.d("Debug onBindViewHolder", artists[position].toString())
            val action =
                ArtistFragmentDirections.actionNavArtistToNavDetailArtist(artists[position])
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }
}