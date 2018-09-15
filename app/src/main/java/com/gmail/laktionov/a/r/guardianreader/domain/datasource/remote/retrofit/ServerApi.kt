package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ServerApi {

    @GET("/search")
    fun getAllArticles(): Call<AllArticleResponse>
}