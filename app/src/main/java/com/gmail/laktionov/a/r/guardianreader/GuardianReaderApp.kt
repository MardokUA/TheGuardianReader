package com.gmail.laktionov.a.r.guardianreader

import android.app.Application
import com.gmail.laktionov.a.r.guardianreader.core.DIManager

class GuardianReaderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        DIManager.injectAll(applicationContext)
    }
}