package com.styleru.muro.androidmsk.Network.DTO

import com.google.gson.annotations.SerializedName

data class Multimedia(
        @SerializedName("url") var url: String,
        @SerializedName("width") var width: Int,
        @SerializedName("height") var height: Int
)