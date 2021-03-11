package com.sun.imagegif.data.source.local

import android.content.Context
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.GifDataSource
import com.sun.imagegif.data.source.local.dao.GifDao

class GifLocalDataSource private constructor(
    private val gifDao: GifDao
) : GifDataSource.Local {

    override fun saveGif(gif: Gif, listener: OnResultDataListener<Gif>) {
        gifDao.save(gif, listener)
    }

    override fun deleteGif(gif: Gif, listener: OnResultDataListener<Gif>) {
        gifDao.delete(gif, listener)
    }

    override fun getGifs() = gifDao.getAll()

    companion object {
        @Volatile
        private var instance: GifLocalDataSource? = null

        fun getInstance(context: Context): GifLocalDataSource =
            instance ?: synchronized(this) {
                instance ?: GifLocalDataSource(
                    GifDao.getInstance(GifImageDatabase.getInstance(context))
                ).also {
                    instance = it
                }
            }
    }
}
