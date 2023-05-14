package com.laad.viniloapp.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.CollectorItemBinding
import com.laad.viniloapp.models.Collector
import com.laad.viniloapp.views.AlbumFragmentDirections
import com.laad.viniloapp.views.CollectorFragmentDirections

class CollectorAdapter : RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>() {

    var collectors :List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
        }
        holder.viewDataBinding.root.setOnClickListener {
            Log.d("Debug onBindViewHolder", collectors[position].toString())
            val action =
                CollectorFragmentDirections.actionNavCollectorToCollectorDetailFragment(collectors[position])
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }


    override fun getItemCount(): Int {
        return collectors.size
    }

    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }
}