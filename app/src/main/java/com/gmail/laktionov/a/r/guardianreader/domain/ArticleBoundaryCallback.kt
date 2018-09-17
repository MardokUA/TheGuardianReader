package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.paging.PagedList
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class ArticleBoundaryCallback(private val remoteRequest: suspend () -> List<ArticleItem>,
                              private val insert: (List<ArticleItem>) -> Unit,
                              private val bgContext: CoroutineContext = CommonPool) : PagedList.BoundaryCallback<ArticleItem>() {

    private var isRequestInProgress = false

    private var lastRequestedPage = 1

    override fun onZeroItemsLoaded() {
        getDataAndSave()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ArticleItem) {
        getDataAndSave()
    }

    private fun getDataAndSave() {
        if (isRequestInProgress) return
        launch(bgContext) {
            isRequestInProgress = true
            val response = remoteRequest()
            if (response.isNotEmpty()) {
                insert(response)
                isRequestInProgress = false
            }
        }
    }
}