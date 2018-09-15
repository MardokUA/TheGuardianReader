package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.room

import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.DBStorage

class RoomDbStorage(private val db: GuardianDatabase) : DBStorage {

    companion object {
        const val DB_NAME = "guardian.db"
    }
}