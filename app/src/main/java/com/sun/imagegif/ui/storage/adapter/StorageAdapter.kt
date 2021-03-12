package com.sun.imagegif.ui.storage.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.imagegif.data.model.Gif

class StorageAdapter : RecyclerView.Adapter<StorageViewHolder>() {

    private val gifs = mutableListOf<Gif>()
    private var listener: OnClickItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        return StorageViewHolder(parent).apply {
            registerItemViewHolderListener(gifs, listener)
        }
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        holder.onBind(gifs[position])
    }

    override fun getItemCount() = gifs.size

    fun updateData(items: MutableList<Gif>?) {
        items?.let {
            gifs.clear()
            gifs.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun setOnClickItemListener(listener: OnClickItemListener) {
        this.listener = listener
    }

    interface OnClickItemListener {

        fun onClickItem(gif: Gif)

        fun onDelete(gif: Gif)
    }
}
