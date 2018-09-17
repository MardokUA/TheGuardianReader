package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote

import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem

interface RemoteSource {

    suspend fun getAllArticles(page: Int): List<ArticleItem>
}