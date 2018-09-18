package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.paging.DataSource
import android.arch.paging.PagedList

interface Repository {

    fun getArticles(): DataSource.Factory<Int, ArticleItem>

    fun getBoundaryCallback(): PagedList.BoundaryCallback<ArticleItem>

    fun getCurrentArticle(articleId: String): ArticleItem
}