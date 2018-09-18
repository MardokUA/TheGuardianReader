package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class ArticleDetailsViewModel(private val repository: Repository,
                              private val bgContext: CoroutineContext = CommonPool) : ViewModel() {

    private val articleData: MutableLiveData<ArticleItem> = MutableLiveData()

    fun observeArticleData(): LiveData<ArticleItem> = articleData

    fun getCurrentArticle(articleId: String) {
        launch(bgContext) {
            articleData.postValue(repository.getCurrentArticle(articleId))
        }
    }
}