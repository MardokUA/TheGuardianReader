package com.gmail.laktionov.a.r.guardianreader.domain.datasource.local

interface StringProvider {

    fun getSuccessMessage(): String
    fun getErrorMessage(): String
}