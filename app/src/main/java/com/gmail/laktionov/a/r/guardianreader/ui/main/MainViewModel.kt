package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class MainViewModel(private val repository: Repository,
                    private val bgContext: CoroutineContext = CommonPool) : ViewModel() {

    private val articlesData: MutableLiveData<PagedList<ArticleListItem>> = MutableLiveData()
    private val paginationConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(IS_PLACEHOLDERS_NEEDED)
            .build()

    fun observeArticles(): LiveData<PagedList<ArticleListItem>> = articlesData

    init {
        getAllArticles()
    }

    private fun getAllArticles() {
        launch(bgContext) {
            val data = repository
                    .getArticles()
                    .map { it -> ArticleUiMapper.mapToArticleListItem(it) }
            val reuslt = LivePagedListBuilder(data, paginationConfig).build()
            articlesData.postValue(reuslt.value)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val PREFETCH_DISTANCE = 3
        private const val IS_PLACEHOLDERS_NEEDED = true
    }
}