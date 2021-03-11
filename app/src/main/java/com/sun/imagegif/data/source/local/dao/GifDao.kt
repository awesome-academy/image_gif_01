package com.sun.imagegif.data.source.local.dao

import android.content.ContentValues
import com.sun.imagegif.R
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.model.User
import com.sun.imagegif.data.source.local.GifImageDatabase
import com.sun.imagegif.data.source.local.OnResultDataListener
import com.sun.imagegif.data.source.local.entry.GifEntryLocal

class GifDao private constructor(private val database: GifImageDatabase) : Dao<Gif> {

    override fun save(obj: Gif, listener: OnResultDataListener<Gif>) {
        val contentValues = ContentValues().apply {
            put(GifEntryLocal.COLUMN_GIF_ID, obj.id)
            put(GifEntryLocal.COLUMN_IMAGE, obj.imageUrl)
            put(GifEntryLocal.COLUMN_TITLE, obj.title)
            put(GifEntryLocal.COLUMN_USERNAME, obj.user.name)
            put(GifEntryLocal.COLUMN_AVATAR, obj.user.avatarUrl)
        }
        database.writableDatabase.replace(GifEntryLocal.TABLE_NAME, null, contentValues).also {
            if (it > 0) {
                listener.onSuccess(obj)
            } else {
                listener.onError(R.string.download_failed)
            }
        }
    }

    override fun getAll(): MutableList<Gif> {
        val gifs = mutableListOf<Gif>()
        val cursor = database.readableDatabase.query(
            GifEntryLocal.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                gifs.add(
                    Gif(
                        id = getString(getColumnIndex(GifEntryLocal.COLUMN_GIF_ID)),
                        imageUrl = getString(getColumnIndex(GifEntryLocal.COLUMN_IMAGE)),
                        title = getString(getColumnIndex(GifEntryLocal.COLUMN_TITLE)),
                        user = User(
                            name = getString(getColumnIndex(GifEntryLocal.COLUMN_USERNAME)),
                            avatarUrl = getString(getColumnIndex(GifEntryLocal.COLUMN_AVATAR)),
                        )
                    )
                )
            }
        }
        return gifs
    }

    override fun delete(obj: Gif, listener: OnResultDataListener<Gif>) {
        database.writableDatabase.delete(
            GifEntryLocal.TABLE_NAME,
            GifEntryLocal.COLUMN_GIF_ID + OPERATOR_CLAUSE,
            arrayOf(obj.id)
        ).also {
            if (it > 0) {
                listener.onSuccess(obj)
            } else {
                listener.onError(R.string.download_failed)
            }
        }
    }

    companion object {
        private const val OPERATOR_CLAUSE = "= ?"

        @Volatile
        private var instance: GifDao? = null

        fun getInstance(database: GifImageDatabase): GifDao =
            instance ?: synchronized(this) {
                instance ?: GifDao(database).also { instance = it }
            }
    }
}
