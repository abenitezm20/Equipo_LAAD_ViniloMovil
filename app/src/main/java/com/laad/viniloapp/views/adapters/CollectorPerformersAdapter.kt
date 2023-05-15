package com.laad.viniloapp.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.CollectorDetailFragmentBinding
import com.laad.viniloapp.databinding.ListPerformersCollectorBinding
import com.laad.viniloapp.models.FavoritePerformers
import com.laad.viniloapp.views.CollectorDetailFragment

class CollectorPerformersAdapter(collectorDetailFragment: CollectorDetailFragment) :
    RecyclerView.Adapter<CollectorPerformersAdapter.CollectorViewHolder>() {

    private var fragment: CollectorDetailFragment? = collectorDetailFragment

    var favoritePerformers: List<FavoritePerformers> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CollectorViewHolder {
        val withDataBinding: ListPerformersCollectorBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), CollectorViewHolder.LAYOUT, parent, false
        )
        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.favoritePerformers = favoritePerformers[position]
            Log.d("CollectorPerformersAdap", favoritePerformers[position].toString())
            bindCover(it, favoritePerformers[position])
        }
        holder.viewDataBinding.root.setOnClickListener {}

    }

    override fun getItemCount(): Int {
        return favoritePerformers.size
    }

    class CollectorViewHolder(val viewDataBinding: ListPerformersCollectorBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_performers_collector
        }
    }

    private fun bindCover(
        viewDataBinding: ListPerformersCollectorBinding,
        performers: FavoritePerformers
    ) {
        fragment?.let {
            Glide.with(it).load(performers.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions().placeholder(R.drawable.loading_image)
                        .error(R.drawable.ic_broken_image)
                ).into(viewDataBinding.imagePerformers)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img_android: ImageView

        init {
            img_android = view.findViewById<View>(R.id.image_performers) as ImageView
        }
    }

}