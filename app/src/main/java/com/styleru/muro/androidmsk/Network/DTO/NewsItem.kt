package com.styleru.muro.androidmsk.Network.DTO

import com.google.gson.annotations.SerializedName

data class NewsItem(
        @SerializedName("section") var section: String? = "",
        @SerializedName("title") var title: String = "",
        @SerializedName("abstract") var abstract: String = "",
        @SerializedName("published_date") var publishedDate: String = "",
        @SerializedName("multimedia") var multimedia: List<Multimedia> = listOf(),
        @SerializedName("url") var url: String = ""
)