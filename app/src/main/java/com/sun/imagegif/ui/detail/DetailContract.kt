package com.sun.imagegif.ui.detail

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.utils.BasePresenter

interface DetailContract {

    interface Presenter : BasePresenter<View> {

        fun saveGif(gif: Gif)

        fun getRelated(keyword: String)
    }

    interface View {

        fun onSaveGifSuccess(gif: Gif)

        fun onSaveGifError(e: Int)

        fun onGetRelatedSuccess(gifs: MutableList<Gif>)

        fun onGetRelatedError(e: Exception?)
    }
}
