package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToArticle
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToPinedArticle
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToPinedItem
import com.gmail.laktionov.a.r.guardianreader.domain.GuardianRepository.ArticleMapper.mapToSingleArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.gmail.laktionov.a.r.guardianreader.domain.SingleArticleItem

class LocalStorage(private val dbStorage: DBStorage,
                   private val keyValueStorage: KeyValueStorage,
                   private val stringProvider: StringProvider) {

    fun getAllArticles(): DataSource.Factory<Int, ArticleItem> {
        return dbStorage.getAllArticles().map { data -> mapToArticleItem(data) }
    }

    fun getCurrentArticle(articleId: String): SingleArticleItem {
        return mapToSingleArticleItem(dbStorage.getCurrentArticle(articleId))
    }

    fun saveArticles(data: List<ArticleItem>) {
        dbStorage.saveArticles(data.map { item -> mapToArticle(item) })
    }

    fun getPinedArticles(): LiveData<List<PinedItem>> {
        return Transformations.map(dbStorage.getPinedArticles()) { article ->
            article.map { mapToPinedItem(it) }
        }
    }

    fun changePinState(currentArticleId: String, isPined: Boolean): Long {
        return dbStorage.changePinedState(mapToPinedArticle(currentArticleId), isPined)
    }

    fun getSuccessMessage(): String = stringProvider.getSuccessMessage()
    fun getErrorMessage(): String = stringProvider.getErrorMessage()
}