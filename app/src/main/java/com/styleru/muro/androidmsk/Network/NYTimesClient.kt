package com.styleru.muro.androidmsk.Network

import com.styleru.muro.androidmsk.Data.NewsItem
import com.styleru.muro.androidmsk.Data.Response
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesClient{
    @GET("/svc/topstories/v2/{section}.{type}")
    fun getNews(@Path("section") section: String, @Path("type") type: String = "json"): Single<Response>
}