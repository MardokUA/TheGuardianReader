package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import android.arch.persistence.room.*

const val DATABASE_VER = 1

/**
 * Simple DAO class
 */
@Dao
interface RoomDao

/**
 * Simple Room database implementation
 */
@Database(
        entities = [Article::class],
        version = DATABASE_VER)
abstract class GuardianDatabase : RoomDatabase() {
    abstract fun getDao(): RoomDao
}

@Entity(tableName = "news")
data class Article(@PrimaryKey(autoGenerate = true)
                   @ColumnInfo(name = "id") val id: Int,
                   @ColumnInfo(name = "article_id") val articleId: String,
                   @ColumnInfo(name = "section_id") val sectionId: String,
                   @ColumnInfo(name = "date") val publicationDate: String,
                   @ColumnInfo(name = "title") val title: String,
                   @ColumnInfo(name = "text") val text: String)