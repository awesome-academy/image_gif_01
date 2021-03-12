package com.sun.imagegif.ui.detail

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.utils.BasePresenter

interface DetailContract {

    interface Presenter : BasePresenter<View> {

        fun saveGif(gif: Gif)
    }

    interface View {

        fun onSaveGifSuccess(gif: Gif)

        fun onSaveGifError(e: Int)
    }
}
