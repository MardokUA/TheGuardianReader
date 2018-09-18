package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.DBStorage

class RoomDbStorage(private val db: GuardianDatabase) : DBStorage {

    override fun getAllArticles(): DataSource.Factory<Int, Article> = db.getDao().getAllArticles()

    override fun saveArticles(data: List<Article>) = db.getDao().insert(data)

    override fun getCurrentArticle(articleId: String): Article  = db.getDao().getArticleContent(articleId)

    companion object {
        const val DB_NAME = "guardian.db"
    }
}