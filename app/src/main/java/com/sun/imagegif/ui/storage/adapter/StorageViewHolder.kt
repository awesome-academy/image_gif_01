package com.sun.imagegif.ui.storage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.imagegif.R
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.utils.loadGifUrl
import kotlinx.android.synthetic.main.item_gif_search.view.gifImageView
import kotlinx.android.synthetic.main.item_gif_storage.view.*

class StorageViewHolder(
    viewGroup: ViewGroup
) : RecyclerView.ViewHolder(newInstance(viewGroup)) {

    fun onBind(gif: Gif) {
        itemView.gifImageView.loadGifUrl(gif.imageUrl)
    }

    fun registerItemViewHolderListener(
        gifs: MutableList<Gif>,
        listener: StorageAdapter.OnClickItemListener?
    ) {
        with(itemView) {
            gifImageView.setOnClickListener {
                listener?.onClickItem(gifs[adapterPosition])
            }
            deleteImageButton.setOnClickListener {
                listener?.onDelete(gifs[adapterPosition])
            }
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_gif_storage, viewGroup, false)
        }
    }
}
