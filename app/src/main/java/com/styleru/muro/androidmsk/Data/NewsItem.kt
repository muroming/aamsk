package com.styleru.muro.androidmsk.Data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsItem(@SerializedName("section") val section: String?,
                    @SerializedName("title") val title: String,
                    @SerializedName("abstract") val abstract: String,
                    @SerializedName("published_date") val publishedDate: String,
                    @SerializedName("multimedia") val multimedia: List<Multimedia>?,
                    @SerializedName("url") val url: String)