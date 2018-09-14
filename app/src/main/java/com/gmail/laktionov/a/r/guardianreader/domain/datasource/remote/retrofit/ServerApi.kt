package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerApi {

    @GET("/search")
    fun getAllArticles(): Call<AllArticleResponse>

    @GET("/{path}")
    fun getSingleArticle(@Path(value = "path") path: String): Call<SingleArticleResponse>
}