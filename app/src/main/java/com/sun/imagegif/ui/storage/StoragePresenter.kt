package com.sun.imagegif.ui.storage

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.local.OnResultDataListener
import com.sun.imagegif.data.source.repositories.GifRepository

class StoragePresenter(private val gifRepository: GifRepository) : StorageContract.Presenter {

    private var view: StorageContract.View? = null

    override fun getGifsLocal() {
        gifRepository.getGifsLocal {
            view?.onGetGifsLocalSuccess(it)
        }
    }

    override fun deleteGifLocal(gif: Gif) {
        gifRepository.deleteGif(gif, object : OnResultDataListener<Gif> {
            override fun onSuccess(data: Gif) {
                view?.onDeleteGifSuccess(data)
            }

            override fun onError(e: Int) {
                view?.onError(e)
            }
        })
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: StorageContract.View?) {
        this.view = view
    }
}
