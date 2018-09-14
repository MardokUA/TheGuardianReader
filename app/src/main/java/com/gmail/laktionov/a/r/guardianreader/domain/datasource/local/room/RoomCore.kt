package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import android.arch.persistence.room.*


/**
 * Simple DAO class
 */
@Dao
interface RoomDao

/**
 * Simple Room database implementation
 */
abstract class GuardianDatabase : RoomDatabase() {
    abstract fun getDao(): RoomDao
}

@Entity(tableName = "news")
data class Article(@PrimaryKey
                   @ColumnInfo(name = "id") val id: Int,
                   @ColumnInfo(name = "article_id") val articleId: String,
                   @ColumnInfo(name = "section_id") val sectionId: String,
                   @ColumnInfo(name = "date") val publicationDate: String,
                   @ColumnInfo(name = "title") val title: String,
                   @ColumnInfo(name = "text") val text: String)