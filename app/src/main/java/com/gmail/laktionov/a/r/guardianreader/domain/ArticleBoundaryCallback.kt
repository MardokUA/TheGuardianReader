package com.gmail.laktionov.a.r.guardianreader.domain

import android.arch.paging.PagedList
import kotlinx.coroutines.channels.SendChannel

class ArticleBoundaryCallback(private val channel: SendChannel<Int>) : PagedList.BoundaryCallback<ArticleItem>() {

    private var lastRequestedPage = 1

    override fun onZeroItemsLoaded() {
        channel.offer(lastRequestedPage)
    }

    override fun onItemAtEndLoaded(itemAtEnd: ArticleItem) {
        channel.offer(lastRequestedPage)
    }

    operator fun inc(): ArticleBoundaryCallback {
        lastRequestedPage++
        return this
    }

}