package com.sun.imagegif.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.imagegif.data.source.local.entry.GifEntryLocal

class GifImageDatabase private constructor(
    private val context: Context
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateGifEntry = "CREATE TABLE ${GifEntryLocal.TABLE_NAME}(" +
                "${GifEntryLocal.COLUMN_GIF_ID} TEXT PRIMARY KEY," +
                "${GifEntryLocal.COLUMN_IMAGE} TEXT," +
                "${GifEntryLocal.COLUMN_TITLE} TEXT," +
                "${GifEntryLocal.COLUMN_USERNAME} TEXT," +
                "${GifEntryLocal.COLUMN_AVATAR} TEXT)"
        db?.execSQL(sqlCreateGifEntry)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "GifImage.db"

        @Volatile
        private var instance: GifImageDatabase? = null

        fun getInstance(context: Context): GifImageDatabase =
            instance ?: synchronized(this) {
                instance ?: GifImageDatabase(context).also { instance = it }
            }
    }
}
