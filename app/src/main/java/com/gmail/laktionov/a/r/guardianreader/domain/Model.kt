package com.gmail.laktionov.a.r.guardianreader.domain

data class ArticleItem(val articleId: String,
                       val sectionId: String,
                       val section: String,
                       val image: String,
                       val publicationDate: String,
                       val title: String,
                       val text: String)

data class PinedItem(val articleId: String,
                     val title: String,
                     val section: String,
                     val isSelected: Boolean = true)