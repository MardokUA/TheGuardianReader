package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToArticle
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToPinedItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem

class LocalStorage(private val dbStorage: DBStorage,
                   private val keyValueStorage: KeyValueStorage) {

    fun getAllArticles(): DataSource.Factory<Int, ArticleItem> {
        return dbStorage.getAllArticles().map { data -> mapToArticleItem(data) }
    }

    fun getCurrentArticle(articleId: String): ArticleItem {
        return mapToArticleItem(dbStorage.getCurrentArticle(articleId))
    }

    fun saveArticles(data: List<ArticleItem>) {
        dbStorage.saveArticles(data.map { item -> mapToArticle(item) })
    }

    fun getPinedArticles(): LiveData<List<PinedItem>> {
        return Transformations.map(dbStorage.getPinedArticles()) { article ->
            article.map { mapToPinedItem(it) }
        }
    }
}