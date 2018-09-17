package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article

interface DBStorage {

    fun getAllArticles(): DataSource.Factory<Int, Article>
    fun saveArticles(data: List<Article>)
}