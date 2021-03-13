package com.sun.imagegif.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.sun.imagegif.BuildConfig
import java.io.File
import java.io.FileOutputStream

class ShareGif(private val context: Context) : AsyncTask<String, Unit, Uri>() {

    override fun doInBackground(vararg params: String?): Uri? {
        try {
            val cacheDir = File(context.cacheDir, PATH_CACHE).also {
                it.mkdirs()
                val fileGif = Glide.with(context)
                    .asFile()
                    .load(params[0])
                    .submit()
                    .get()
                FileOutputStream("$it/$FILE_GIF").apply {
                    write(fileGif.readBytes())
                    close()
                }
            }
            return FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + PROVIDER,
                File(cacheDir, FILE_GIF)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: Uri?) {
        super.onPostExecute(result)
        result?.let {
            Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                setDataAndType(it, context.contentResolver.getType(it))
                putExtra(Intent.EXTRA_STREAM, it)
                type = TYPE_MIME
            }.also { intent ->
                context.startActivity(intent)
            }
        }
    }

    companion object {
        private const val FILE_GIF = "sharing.gif"
        private const val TYPE_MIME = "image/gif"
        private const val PROVIDER = ".provider"
        private const val PATH_CACHE = "sharing_gif_disk_cache"
    }
}
