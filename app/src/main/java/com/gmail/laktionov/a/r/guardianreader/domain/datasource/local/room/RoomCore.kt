package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*

const val DATABASE_VER = 2

@Dao
interface RoomDao {

    //Article
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Article>)

    @Query("SELECT * FROM news WHERE article_id IS :articleId")
    fun getArticleContent(articleId: String): Article

    @Transaction
    @Query("SELECT * FROM news WHERE article_id IS :articleId")
    fun getSingleArticle(articleId: String): SingleArticle

    @Query("SELECT * FROM news")
    fun getAllArticles(): DataSource.Factory<Int, Article>

    //PinedArticle
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pinedArticle: PinedArticle): Long

    @Query("SELECT pined_articles.article_id, news.* FROM pined_articles LEFT JOIN news ON pined_articles.article_id LIKE news.article_id ")
    fun getPinedArticles(): LiveData<List<Article>>

    @Delete
    fun deletePinedArticle(pinedArticle: PinedArticle): Int
}

/**
 * Simple Room database implementation
 */
@Database(
        entities = [Article::class, PinedArticle::class],
        version = DATABASE_VER)
abstract class GuardianDatabase : RoomDatabase() {
    abstract fun getDao(): RoomDao
}

@Entity(tableName = "news")
data class Article(@PrimaryKey
                   @ColumnInfo(name = "article_id") val articleId: String,
                   @ColumnInfo(name = "section_id") val sectionId: String,
                   @ColumnInfo(name = "section") val section: String,
                   @ColumnInfo(name = "image") val image: String,
                   @ColumnInfo(name = "date") val publicationDate: String,
                   @ColumnInfo(name = "title") val title: String,
                   @ColumnInfo(name = "text") val text: String)

@Entity(tableName = "pined_articles")
data class PinedArticle(@PrimaryKey
                        @ColumnInfo(name = "article_id") val articleId: String)

class SingleArticle {

    @Embedded
    lateinit var article: Article
    @Relation(
            parentColumn = "article_id",
            entityColumn = "article_id")
    var pinedArticle: List<PinedArticle> = mutableListOf()
}