package com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Simple OkHttp interceptor to add API key in request header
 *
 * @see [Interceptor]
 */
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}