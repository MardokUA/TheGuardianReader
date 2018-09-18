package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.LocalStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteStorage

class GuardianRepository(private val remoteStorage: RemoteStorage,
                         private val localStorage: LocalStorage) : Repository {

    override fun getArticles(): DataSource.Factory<Int, ArticleItem> {
        return localStorage.getAllArticles()
    }

    override fun getBoundaryCallback(): PagedList.BoundaryCallback<ArticleItem> {
        return ArticleBoundaryCallback({ page -> remoteStorage.getAllArticles(page) }, { localStorage.saveArticles(it) })
    }

    override fun getCurrentArticle(articleId: String): ArticleItem {
        return localStorage.getCurrentArticle(articleId)
    }

    override fun getPinedArticles(): LiveData<List<PinedItem>> {
        return localStorage.getPinedArticles()
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
    }
}