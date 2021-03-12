package com.sun.imagegif.ui.storage

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.utils.BasePresenter

interface StorageContract {

    interface Presenter : BasePresenter<View> {

        fun getGifsLocal()
    }

    interface View {

        fun onGetGifsLocalSuccess(data: MutableList<Gif>)
    }
}
