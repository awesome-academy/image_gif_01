package com.sun.imagegif.data.source

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.local.OnResultDataListener
import com.sun.imagegif.data.source.remote.OnFetchDataJsonListener

interface GifDataSource {

    interface Local {

        fun saveGif(gif: Gif, listener: OnResultDataListener<Gif>)

        fun deleteGif(gif: Gif, listener: OnResultDataListener<Gif>)

        fun getGifs(): MutableList<Gif>
    }

    interface Remote {

        fun getTrending(listener: OnFetchDataJsonListener<MutableList<Gif>>)

        fun getRandom(listener: OnFetchDataJsonListener<MutableList<Gif>>)

        fun searchWithGif(
            keyword: String,
            listener: OnFetchDataJsonListener<MutableList<Gif>>
        )

        fun searchWithText(
            keyword: String,
            listener: OnFetchDataJsonListener<MutableList<Gif>>
        )
    }
}
