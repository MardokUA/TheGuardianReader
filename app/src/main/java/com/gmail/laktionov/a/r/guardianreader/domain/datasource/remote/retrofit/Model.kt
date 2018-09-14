package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import com.google.gson.annotations.SerializedName

interface GuardianResponse {
    val status: String
    val userTier: String
    val total: Int
}

data class AllArticleResponse(override val status: String,
                              override val userTier: String,
                              override val total: Int,
                              val startIndex: Int,
                              val pageSize: Int,
                              val currentPage: Int,
                              val pages: Int,
                              val orderBy: String,
                              val results: List<ArticleResponse>) : GuardianResponse

data class SingleArticleResponse(override val status: String,
                                 override val userTier: String,
                                 override val total: Int,
                                 @SerializedName("content") val data: ArticleResponse) : GuardianResponse


data class ArticleResponse(val id: String,
                           val sectionId: String,
                           val sectionName: String,
                           val webPublicationDate: String,
                           val webTitle: String,
                           val webUrl: String,
                           val apiUrl: String)

