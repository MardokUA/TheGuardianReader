package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import com.gmail.laktionov.a.r.guardianreader.core.await
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit.RetrofitServerStorage.ResponseMapper.mapToArticle

class RetrofitServerStorage(private val serverApi: ServerApi) : RemoteSource {

    override suspend fun getAllArticles(page: Int): List<ArticleItem> {
        val response = serverApi.getAllArticles(page).await()

        return if (response.isOk()) response.data!!.response.results.map { item ->
            mapToArticle(item)
        } else mutableListOf()
    }

    object ResponseMapper {

        fun mapToArticle(response: ArticleResponse) =
                ArticleItem(articleId = response.id,
                        publicationDate = response.webPublicationDate,
                        section = response.sectionName,
                        sectionId = response.sectionId,
                        title = response.webTitle,
                        image = response.additionalField.image,
                        text = response.additionalField.rawText)
    }
}