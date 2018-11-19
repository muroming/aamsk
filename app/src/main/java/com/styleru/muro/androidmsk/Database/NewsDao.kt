package com.styleru.muro.androidmsk.Database

import android.arch.persistence.room.*
import com.styleru.muro.androidmsk.Database.Data.NewsEntity
import com.styleru.muro.androidmsk.Network.DTO.NewsItem

@Dao
interface NewsDao {
    @Query("select * from NewsEntity where section = :section")
    fun getAllBySection(section: String): List<NewsEntity>

    @Query("select * from NewsEntity where id = :id")
    fun getById(id: Long): NewsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: Array<NewsEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NewsEntity): Long

    @Delete
    fun delete(entity: NewsEntity)

    @Update
    fun update(entity: NewsEntity)
}