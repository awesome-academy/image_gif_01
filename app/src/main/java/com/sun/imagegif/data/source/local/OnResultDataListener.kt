package com.sun.imagegif.data.source.local

interface OnResultDataListener<T> {

    fun onSuccess(data: T)

    fun onError(e: Int)
}
