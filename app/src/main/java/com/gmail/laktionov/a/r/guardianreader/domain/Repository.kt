package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article

interface Repository {

    fun getArticles(): DataSource.Factory<Int, Article>
}