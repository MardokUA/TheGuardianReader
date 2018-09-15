package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.preference

import android.content.SharedPreferences
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.KeyValueStorage

class SharedPrefs(private val sharedPrefs: SharedPreferences) : KeyValueStorage {

    companion object {
        const val SHARED_STORAGE = "guardian_storage"
    }
}