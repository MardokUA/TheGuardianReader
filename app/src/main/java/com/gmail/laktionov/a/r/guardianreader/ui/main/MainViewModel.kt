package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import kotlinx.coroutines.experimental.CommonPool
import kotlin.coroutines.experimental.CoroutineContext

class MainViewModel(private val repository: Repository,
                    private val bgContext: CoroutineContext = CommonPool) : ViewModel() {

    private val paginationConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(IS_PLACEHOLDERS_NEEDED)
            .build()

    private val articlesData: LiveData<PagedList<ArticleItem>> = transformPersistedResult()
    private val pinedData: LiveData<List<PinedItem>> = getPinedItems()

    fun observeArticles(): LiveData<PagedList<ArticleItem>> = articlesData
    fun observePinnedArticles(): LiveData<List<PinedItem>> = pinedData

    fun getArticles() = articlesData.value

    private fun transformPersistedResult(): LiveData<PagedList<ArticleItem>> {
        return LivePagedListBuilder(repository.getArticles(), paginationConfig)
                .setBoundaryCallback(repository.getBoundaryCallback())
                .build()
    }

    private fun getPinedItems(): LiveData<List<PinedItem>> {
        return repository.getPinedArticles()
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val PREFETCH_DISTANCE = 5
        private const val IS_PLACEHOLDERS_NEEDED = true
    }
}