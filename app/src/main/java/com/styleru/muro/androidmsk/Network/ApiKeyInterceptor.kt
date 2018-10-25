package com.styleru.muro.androidmsk.Network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWOKey = chain.request()

        val url = requestWOKey.url()
                .newBuilder()
                .addQueryParameter("api-key", apiKey)
                .build()

        val requestWithKey = requestWOKey
                .newBuilder()
                .url(url)
                .build()

        return chain.proceed(requestWithKey)
    }
}