package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToArticle
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToArticleItem

class LocalStorage(private val dbStorage: DBStorage,
                   private val keyValueStorage: KeyValueStorage) {

    fun getAllArticles(): DataSource.Factory<Int, ArticleItem> {
        return dbStorage.getAllArticles().map { data -> mapToArticleItem(data) }
    }

    fun saveArticles(data: List<ArticleItem>) {
        dbStorage.saveArticles(data.map { item -> mapToArticle(item) })
    }
}