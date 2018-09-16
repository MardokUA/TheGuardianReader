package com.gmail.laktionov.a.r.guardianreader.ui.main

import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article

object ArticleUiMapper {

    fun mapToArticleListItem(source: Article) =
            ArticleListItem(
                    title = source.title,
                    section = source.section,
                    imageUrl = source.image)
}

data class ArticleListItem(val title: String,
                           val section: String,
                           val imageUrl: String)