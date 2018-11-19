package com.styleru.muro.androidmsk.Network

import com.styleru.muro.androidmsk.Network.DTO.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NYTimesClient{
    @GET("/svc/topstories/v2/{section}.{type}")
    fun getNews(@Path("section") section: String, @Path("type") type: String = "json"): Single<Response>
}