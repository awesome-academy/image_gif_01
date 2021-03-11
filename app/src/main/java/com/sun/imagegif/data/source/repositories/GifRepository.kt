package com.sun.imagegif.data.source.repositories

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.GifDataSource
import com.sun.imagegif.data.source.local.OnResultDataListener
import com.sun.imagegif.data.source.remote.OnFetchDataJsonListener

class GifRepository private constructor(
    private val remote: GifDataSource.Remote,
    private val local: GifDataSource.Local
) {

    fun getTrending(listener: OnFetchDataJsonListener<MutableList<Gif>>) =
        remote.getTrending(listener)

    fun getRandom(listener: OnFetchDataJsonListener<MutableList<Gif>>) =
        remote.getRandom(listener)

    fun searchWithGif(
        keyword: String,
        listener: OnFetchDataJsonListener<MutableList<Gif>>
    ) {
        remote.searchWithGif(keyword, listener)
    }

    fun searchWithText(
        keyword: String,
        listener: OnFetchDataJsonListener<MutableList<Gif>>
    ) {
        remote.searchWithText(keyword, listener)
    }

    fun saveGif(gif: Gif, listener: OnResultDataListener<Gif>) {
        local.saveGif(gif, listener)
    }

    fun deleteGif(gif: Gif, listener: OnResultDataListener<Gif>) {
        local.deleteGif(gif, listener)
    }

    fun getGifsLocal(data: (MutableList<Gif>) -> Unit) {
        data(local.getGifs())
    }

    companion object {
        @Volatile
        private var instance: GifRepository? = null

        fun getInstance(
            local: GifDataSource.Local,
            remote: GifDataSource.Remote
        ): GifRepository = instance ?: synchronized(this) {
            instance ?: GifRepository(remote, local).also {
                instance = it
            }
        }
    }
}
