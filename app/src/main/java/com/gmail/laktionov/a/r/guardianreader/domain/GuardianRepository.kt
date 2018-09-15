package com.gmail.laktionov.a.r.guardianreader.domain

import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.LocalStorage
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.RemoteStorage

class GuardianRepository(private val remoteStorage: RemoteStorage,
                         private val localStorage: LocalStorage) : Repository {

}