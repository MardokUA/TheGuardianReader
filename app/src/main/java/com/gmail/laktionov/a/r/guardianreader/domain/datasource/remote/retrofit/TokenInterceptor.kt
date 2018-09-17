package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import com.gmail.laktionov.a.r.guardianreader.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Simple OkHttp interceptor to add API key in request header
 *
 * @see [Interceptor]
 */
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (chain.request().header(ACCESS_TOKEN_HEADER) == null) {
            requestBuilder.addHeader(ACCESS_TOKEN_HEADER, BuildConfig.API_KEY)
        }
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val ACCESS_TOKEN_HEADER = "api-key"
    }
}