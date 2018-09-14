package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.preference

import android.content.Context
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.KeyValueStorage

class SharedPrefs(context: Context) : KeyValueStorage {

    private val preferences = context.getSharedPreferences(SHARED_STORAGE, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_STORAGE = "guardian_storage"
    }
}