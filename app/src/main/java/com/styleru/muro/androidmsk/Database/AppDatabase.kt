package com.styleru.muro.androidmsk.Database

import android.arch.persistence.room.*
import android.content.Context
import com.styleru.muro.androidmsk.DATABASE_NAME
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.Network.DTO.NewsItem

@Database(entities = [NewsEntity::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return instance ?: throw NullPointerException("AppDatabase is null")
        }
    }

    abstract fun newsDao(): NewsDao
}