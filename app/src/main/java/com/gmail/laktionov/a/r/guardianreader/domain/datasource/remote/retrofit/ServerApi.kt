package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("/search")
    fun getAllArticles(@Query("page") page: Int,
                       @Query("page-size") pageSize: Int = 10,
                       @Query("show-fields") fields: String = "bodyText,thumbnail"): Call<GuardianBaseResponse>
}