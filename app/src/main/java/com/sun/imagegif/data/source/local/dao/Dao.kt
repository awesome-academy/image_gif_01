package com.sun.imagegif.data.source.local.dao

import com.sun.imagegif.data.source.local.OnResultDataListener

interface Dao<T> {

    fun save(obj: T, listener: OnResultDataListener<T>)

    fun getAll(): MutableList<T>

    fun delete(obj: T, listener: OnResultDataListener<T>)
}
