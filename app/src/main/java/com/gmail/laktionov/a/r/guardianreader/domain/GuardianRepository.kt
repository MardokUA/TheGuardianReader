package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.LocalStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.PinedArticle
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.SingleArticle
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteStorage

class GuardianRepository(private val remoteStorage: RemoteStorage,
                         private val localStorage: LocalStorage) : Repository {

    override fun getArticles(): DataSource.Factory<Int, ArticleItem> =
            localStorage.getAllArticles()

    override suspend fun getArticlesByPage(page: Int) =
            remoteStorage.getAllArticles(page)

    override fun saveArticles(data: List<ArticleItem>) =
            localStorage.saveArticles(data)

    override fun getCurrentArticle(articleId: String): SingleArticleItem =
            localStorage.getCurrentArticle(articleId)

    override fun getPinedArticles(): LiveData<List<PinedItem>> =
            localStorage.getPinedArticles()

    override fun changePinState(currentArticleId: String, isPined: Boolean): Message {
        val isSuccess = localStorage.changePinState(currentArticleId, isPined) > 0
        val message = if (isSuccess) localStorage.getSuccessMessage() else localStorage.getErrorMessage()
        return Message(message, isSuccess)
    }

    object ArticleMapper {

        fun mapToArticleItem(source: Article) =
                ArticleItem(
                        title = source.title,
                        section = source.section,
                        text = source.text,
                        image = source.image,
                        sectionId = source.sectionId,
                        publicationDate = source.publicationDate,
                        articleId = source.articleId)

        fun mapToArticle(response: ArticleItem): Article =
                Article(
                        articleId = response.articleId,
                        publicationDate = response.publicationDate,
                        section = response.section,
                        sectionId = response.sectionId,
                        title = response.title,
                        image = response.image,
                        text = response.text)

        fun mapToPinedItem(source: Article) =
                PinedItem(articleId = source.articleId,
                        title = source.title,
                        section = source.section)

        fun mapToSingleArticleItem(source: SingleArticle) =
                SingleArticleItem(item = mapToArticleItem(source.article),
                        isSelected = !source.pinedArticle.isEmpty())

        fun mapToPinedArticle(articleId: String) = PinedArticle(articleId)
    }
}