package com.styleru.muro.androidmsk.Network.DTO

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("num_results") val numResults: Int,
               @SerializedName("results") val results: List<NewsItem>)