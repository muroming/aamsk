package com.styleru.muro.androidmsk.Data

import com.google.gson.annotations.SerializedName

data class Multimedia(@SerializedName("url") val url: String,
                      @SerializedName("width") val width: Int,
                      @SerializedName("height") val height: Int,
                      @SerializedName("subtype") val subtype: String)