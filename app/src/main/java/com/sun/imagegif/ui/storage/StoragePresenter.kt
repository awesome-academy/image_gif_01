package com.sun.imagegif.ui.storage

import com.sun.imagegif.data.source.repositories.GifRepository

class StoragePresenter(private val gifRepository: GifRepository) : StorageContract.Presenter {

    private var view: StorageContract.View? = null

    override fun getGifsLocal() {
        gifRepository.getGifsLocal {
            view?.onGetGifsLocalSuccess(it)
        }
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: StorageContract.View?) {
        this.view = view
    }
}
