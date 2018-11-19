package com.styleru.muro.androidmsk.Database.Data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.styleru.muro.androidmsk.Network.DTO.Multimedia

@Entity
data class NewsEntity(
        @PrimaryKey(autoGenerate = true) var id: Long? = null,
        @SerializedName("section") var section: String? = "",
        @SerializedName("title") var title: String = "",
        @SerializedName("abstract") var abstract: String = "",
        @SerializedName("published_date") var publishedDate: String = "",
        @SerializedName("multimedia") var multimedia: List<Multimedia> = emptyList(),
        @SerializedName("url") var url: String = ""
)