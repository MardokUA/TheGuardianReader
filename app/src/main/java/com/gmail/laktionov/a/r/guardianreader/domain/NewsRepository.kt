package com.gmail.laktionov.a.r.guardianreader.domain

import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.LocalStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteStorage

class NewsRepository(private val remoteStorage: RemoteStorage,
                     private val localStorage: LocalStorage) : Repository {


}