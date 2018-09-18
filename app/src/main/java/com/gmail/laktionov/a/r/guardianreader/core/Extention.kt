package com.gmail.laktionov.a.r.guardianreader.core

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import com.gmail.laktionov.a.r.guardianreader.domain.datasource.remote.retrofit.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.suspendCoroutine

fun <T : ViewModel> AppCompatActivity.obtainViewModel(modelClass: Class<T>): T {
    return ViewModelProviders.of(this, GuardianViewModelFactory.INSTANSE).get(modelClass)
}

fun AppCompatActivity.isLolipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

/**
 * Extension , that provides retrofit call in in own thread and wraps it in [BaseResponse]
 */
suspend fun <T : Any> Call<T>.await(): BaseResponse<T> = suspendCoroutine { continuation ->
    enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            continuation.resume(BaseResponse(response.body()))
        }

        override fun onFailure(call: Call<T>?, t: Throwable) = continuation.resume(BaseResponse(null))
    })
}

/**
 * Performs the given action when the view tree is about to be drawn.
 */
inline fun View.doOnPreDraw(crossinline action: (view: View) -> Unit) {
    val vto = viewTreeObserver
    vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            action(this@doOnPreDraw)
            when {
                vto.isAlive -> vto.removeOnPreDrawListener(this)
                else -> viewTreeObserver.removeOnPreDrawListener(this)
            }
            return true
        }
    })
}
