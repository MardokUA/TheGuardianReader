package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.DBStorage

class RoomDbStorage(private val db: GuardianDatabase) : DBStorage {

    override fun getAllArticles(): DataSource.Factory<Int, Article> = db.getDao().getAllArticles()
    override fun saveArticles(data: List<Article>) = db.getDao().insert(data)
    override fun getCurrentArticle(articleId: String): SingleArticle = db.getDao().getSingleArticle(articleId)

    override fun getPinedArticles(): LiveData<List<Article>> = db.getDao().getPinedArticles()
    override fun changePinedState(article: PinedArticle, isPined: Boolean): Long {
        return if (isPined) {
            db.getDao().insert(article)
        } else {
            db.getDao().deletePinedArticle(article).toLong()
        }
    }

    companion object {
        const val DB_NAME = "guardian.db"
    }
}