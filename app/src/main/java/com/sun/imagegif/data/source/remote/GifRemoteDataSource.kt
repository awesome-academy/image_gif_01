package com.sun.imagegif.data.source.remote

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.model.GifEntry
import com.sun.imagegif.data.source.GifDataSource
import com.sun.imagegif.data.source.remote.fetchjson.GetJsonFromUrl
import com.sun.imagegif.utils.Constant

class GifRemoteDataSource private constructor() : GifDataSource.Remote {

    override fun getTrending(listener: OnFetchDataJsonListener<MutableList<Gif>>) {
        val trendingUrl = Constant.BASE_URL +
                Constant.TYPE_GIF +
                Constant.TRENDING +
                Constant.BASE_API_KEY +
                Constant.LIMIT
        GetJsonFromUrl(listener, GifEntry.GIF).execute(trendingUrl)
    }

    override fun getRandom(listener: OnFetchDataJsonListener<MutableList<Gif>>) {
        val randomUrl = Constant.BASE_URL +
                Constant.TYPE_GIF +
                Constant.TRENDING +
                Constant.BASE_API_KEY +
                Constant.LIMIT +
                Constant.OFFSET +
                Constant.PAGE.toString()
        GetJsonFromUrl(listener, GifEntry.GIF).execute(randomUrl)
    }

    override fun searchWithGif(
        keyword: String,
        listener: OnFetchDataJsonListener<MutableList<Gif>>
    ) {
        val searchUrl = Constant.BASE_URL +
                Constant.TYPE_GIF +
                Constant.SEARCH +
                Constant.BASE_API_KEY +
                Constant.KEYWORD +
                keyword +
                Constant.LIMIT
        GetJsonFromUrl(listener, GifEntry.GIF).execute(searchUrl)
    }

    override fun searchWithText(
        keyword: String,
        listener: OnFetchDataJsonListener<MutableList<Gif>>
    ) {
        val searchUrl = Constant.BASE_URL +
                Constant.TYPE_TEXT +
                Constant.SEARCH +
                Constant.BASE_API_KEY +
                Constant.KEYWORD +
                keyword +
                Constant.LIMIT
        GetJsonFromUrl(listener, GifEntry.GIF).execute(searchUrl)
    }

    companion object {
        @Volatile
        private var instance: GifRemoteDataSource? = null

        fun getInstance(): GifRemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: GifRemoteDataSource().also {
                    instance = it
                }
            }
    }
}
