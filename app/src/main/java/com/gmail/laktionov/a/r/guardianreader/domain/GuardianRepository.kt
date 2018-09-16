package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.paging.DataSource
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.LocalStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room.Article
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteStorage

class GuardianRepository(private val remoteStorage: RemoteStorage,
                         private val localStorage: LocalStorage) : Repository {


    override fun getArticles(): DataSource.Factory<Int, Article> {
        return localStorage.getAllArticles()

    }
}