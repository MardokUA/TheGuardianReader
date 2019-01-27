package com.gmail.laktionov.a.r.guardianreader.core

import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    protected val scope = CoroutineScope(dispatcher + Job())

    override fun onCleared() {
        super.onCleared()
        scope.coroutineContext.cancel()
    }

    companion object {
        @VisibleForTesting
        var dispatcher: CoroutineDispatcher = Dispatchers.IO
    }
}