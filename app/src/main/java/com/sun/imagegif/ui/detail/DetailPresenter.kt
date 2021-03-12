package com.sun.imagegif.ui.detail

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.local.OnResultDataListener
import com.sun.imagegif.data.source.repositories.GifRepository

class DetailPresenter(private val gifRepository: GifRepository) : DetailContract.Presenter {

    private var view: DetailContract.View? = null

    override fun saveGif(gif: Gif) {
        gifRepository.saveGif(gif, object : OnResultDataListener<Gif> {

            override fun onSuccess(data: Gif) {
                view?.onSaveGifSuccess(data)
            }

            override fun onError(e: Int) {
                view?.onSaveGifError(e)
            }
        })
    }

    override fun onStart() = Unit

    override fun onStop() = Unit

    override fun setView(view: DetailContract.View?) {
        this.view = view
    }
}
