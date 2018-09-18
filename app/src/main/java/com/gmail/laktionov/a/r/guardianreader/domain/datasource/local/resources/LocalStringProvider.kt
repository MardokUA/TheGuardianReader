package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.resources

import android.content.res.Resources
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.local.StringProvider

class LocalStringProvider(private val resources: Resources) : StringProvider {

    override fun getSuccessMessage(): String = resources.getString(R.string.success_message)
    override fun getErrorMessage(): String = resources.getString(R.string.error_message)
}