package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote

import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem

class RemoteStorage(private val remoteSource: RemoteSource) {

    suspend fun getAllArticles(): List<ArticleItem> {
        return remoteSource.getAllArticles()
    }
}