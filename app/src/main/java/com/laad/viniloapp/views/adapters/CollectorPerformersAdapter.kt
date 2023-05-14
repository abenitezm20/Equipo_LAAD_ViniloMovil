package com.laad.viniloapp.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.PerformersCollectorItemBinding
import com.laad.viniloapp.models.FavoritePerformers

class CollectorPerformersAdapter() : RecyclerView.Adapter<CollectorPerformersAdapter.CollectorViewHolder>() {

    var favoritePerformers :List<FavoritePerformers> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectorViewHolder {
        val withDataBinding: PerformersCollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.favoritePerformers = favoritePerformers[position]
            Log.d("Debug onBindViewHolder Album", favoritePerformers[position].toString())
        }
        holder.viewDataBinding.root.setOnClickListener {
        }
    }


    override fun getItemCount(): Int {
        return favoritePerformers.size
    }

    class CollectorViewHolder(val viewDataBinding: PerformersCollectorItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_performers_collector
        }
    }
}