package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.gmail.laktionov.a.r.guardianreader.core.BaseViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleBoundaryCallback
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import kotlinx.coroutines.channels.actor

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    private val paginationConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(IS_PLACEHOLDERS_NEEDED)
            .build()

    private val actionChannel = scope.actor<Int> {
        for (page in channel) loadData(page)
    }
    private var boundaryCallback = ArticleBoundaryCallback(actionChannel)

    fun observeArticles(): LiveData<PagedList<ArticleItem>> = transformPersistedResult()
    fun observePinnedArticles(): LiveData<List<PinedItem>> = repository.getPinedArticles()

    private suspend fun loadData(page: Int) {
        val response = repository.getArticlesByPage(page)
        if (response.isNotEmpty()) {
            repository.saveArticles(response)
            boundaryCallback++
        }
    }

    private fun transformPersistedResult(): LiveData<PagedList<ArticleItem>> {
        return LivePagedListBuilder(repository.getArticles(), paginationConfig)
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 10
        private const val IS_PLACEHOLDERS_NEEDED = true
    }
}