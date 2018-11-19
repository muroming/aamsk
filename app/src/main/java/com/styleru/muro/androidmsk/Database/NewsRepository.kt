package com.styleru.muro.androidmsk.Database

import android.content.Context
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.Network.DTO.NewsItem
import io.reactivex.Completable
import io.reactivex.Single

object NewsRepository {
    fun saveItems(context: Context, items: List<NewsItem>): Completable =
            Completable.fromCallable {
                val db = AppDatabase.getInstance(context)
                db.newsDao().insertAll(DatabaseConverter.fromItemToEntity(*items.toTypedArray()).toTypedArray())
            }

    fun updateItem(context: Context, newsItem: NewsEntity): Completable =
            Completable.fromCallable {
                AppDatabase.getInstance(context)
                        .newsDao().update(newsItem)
            }

    fun getItems(context: Context, section: String): Single<List<NewsEntity>> =
            Single.fromCallable {
                AppDatabase.getInstance(context).newsDao().getAllBySection(section)
            }

    fun getItemById(context: Context, id: Long): Single<NewsEntity> =
            Single.fromCallable {
                AppDatabase.getInstance(context).newsDao().getById(id)
            }
}