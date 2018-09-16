package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.DBStorage

class RoomDbStorage(private val db: GuardianDatabase) : DBStorage {

    override fun getAllArticles(): DataSource.Factory<Int, Article> = db.getDao().getAllArticles()

    companion object {
        const val DB_NAME = "guardian.db"
    }

}