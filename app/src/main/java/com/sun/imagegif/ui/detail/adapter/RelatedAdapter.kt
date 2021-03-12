package com.sun.imagegif.ui.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.ui.search.adapter.SearchViewHolder

class RelatedAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    private val gifs = mutableListOf<Gif>()
    private var listener: ((Gif) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(parent).apply {
            registerItemViewHolderListener {
                listener?.let { func -> func(gifs[it]) }
            }
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(gifs[position])
    }

    override fun getItemCount() = gifs.size

    fun addRelated(items: MutableList<Gif>?) {
        items?.let {
            gifs.addAll(it)
            notifyItemRangeInserted(this.gifs.size - it.size, it.size)
        }
    }

    fun setOnClickItemListener(listener: ((Gif) -> Unit)?) {
        this.listener = listener
    }
}
