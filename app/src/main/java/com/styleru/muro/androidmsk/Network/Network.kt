package com.styleru.muro.androidmsk.Network

import com.styleru.muro.androidmsk.BASE_URL
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Network private constructor() {
    private object holder {
        val instance = Network()
    }

    private val client: NYTimesClient
    private val okHttp: OkHttpClient
    private val retrofit: Retrofit
    private val apiKey = "8b48059eabb04f098252e3ddebd6a1f5"

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        okHttp = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .addInterceptor(ApiKeyInterceptor(apiKey))
                .build()

        retrofit = Retrofit.Builder()
                .client(okHttp)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

        client = retrofit.create(NYTimesClient::class.java)
    }

    companion object {
        val instance: Network by lazy { holder.instance }
    }

    fun getApiClient(): NYTimesClient = client
}