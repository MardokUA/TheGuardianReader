package com.gmail.laktionov.a.r.guardianreader.core

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

fun <T : ViewModel> AppCompatActivity.obtainViewModel(modelClass: Class<T>): T {
    return ViewModelProviders.of(this, GuardianViewModelFactory.INSTANSE).get(modelClass)
}