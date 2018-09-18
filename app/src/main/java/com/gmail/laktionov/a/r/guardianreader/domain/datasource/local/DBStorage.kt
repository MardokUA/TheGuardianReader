package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.PinedArticle
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.SingleArticle

interface DBStorage {

    fun getAllArticles(): DataSource.Factory<Int, Article>
    fun saveArticles(data: List<Article>)
    fun getCurrentArticle(articleId: String): SingleArticle

    fun getPinedArticles(): LiveData<List<Article>>

    fun changePinedState(article: PinedArticle, isPined: Boolean): Long
}