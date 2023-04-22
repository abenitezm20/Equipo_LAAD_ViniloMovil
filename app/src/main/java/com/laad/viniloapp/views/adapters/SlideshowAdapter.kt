package com.laad.viniloapp.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laad.viniloapp.R
import com.laad.viniloapp.models.PruebaDTO

class SlideshowAdapter() : RecyclerView.Adapter<SlideshowAdapter.SlideshowViewHolder>() {

    var items: List<PruebaDTO> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SlideshowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.nombre_item)
        fun bind(word: PruebaDTO) {
            textViewItem.text = word.nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideshowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prueba, parent, false)
        return SlideshowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SlideshowViewHolder, position: Int) {
        holder.bind(items[position])
    }

}
