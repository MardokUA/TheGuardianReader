package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.Repository

class ArticleDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val articleData: MutableLiveData<ArticleItem> = MutableLiveData()

    fun getCurrentArticle(articleId: String) {

    }

}