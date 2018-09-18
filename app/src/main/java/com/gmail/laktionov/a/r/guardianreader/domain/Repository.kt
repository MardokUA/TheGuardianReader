package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList

interface Repository {

    fun getArticles(): DataSource.Factory<Int, ArticleItem>
    fun getCurrentArticle(articleId: String): ArticleItem
    fun getBoundaryCallback(): PagedList.BoundaryCallback<ArticleItem>

    fun getPinedArticles(): LiveData<List<PinedItem>>

}