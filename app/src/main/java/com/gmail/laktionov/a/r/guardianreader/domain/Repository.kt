package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource

interface Repository {

    fun getArticles(): DataSource.Factory<Int, ArticleItem>

    suspend fun getArticlesByPage(page: Int): List<ArticleItem>

    fun saveArticles(data: List<ArticleItem>)

    fun getCurrentArticle(articleId: String): SingleArticleItem

    fun getPinedArticles(): LiveData<List<PinedItem>>

    fun changePinState(currentArticleId: String, isPined: Boolean): Message

}