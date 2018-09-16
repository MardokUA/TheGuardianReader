package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article

class LocalStorage(private val dbStorage: DBStorage,
                   private val keyValueStorage: KeyValueStorage) {

    fun getAllArticles(): DataSource.Factory<Int, Article> = dbStorage.getAllArticles()
}