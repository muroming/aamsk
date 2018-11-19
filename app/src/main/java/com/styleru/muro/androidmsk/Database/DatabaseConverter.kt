package com.styleru.muro.androidmsk.Database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.Network.DTO.Multimedia
import com.styleru.muro.androidmsk.Network.DTO.NewsItem

object DatabaseConverter {

    fun fromItemToEntity(vararg items: NewsItem): List<NewsEntity> =
            items.map { item ->
                NewsEntity(null, item.section, item.title,
                        item.abstract, item.publishedDate, item.multimedia, item.url)
            }


    fun fromEntityToItem(vararg items: NewsEntity): List<NewsItem> =
            items.map { item ->
                NewsItem(item.section, item.title,
                        item.abstract, item.publishedDate,
                        item.multimedia, item.url)
            }

    @TypeConverter
    @JvmStatic
    fun fromMultimediaToString(list: List<Multimedia>): String =
            Gson().toJson(list)

    @TypeConverter
    @JvmStatic
    fun fromStringToMultimedia(string: String): List<Multimedia> =
            Gson().fromJson(string, object : TypeToken<List<Multimedia>>() {}.type)
}