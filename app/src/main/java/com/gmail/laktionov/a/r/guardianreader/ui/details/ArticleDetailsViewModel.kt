package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.gmail.laktionov.a.r.guardianreader.core.BaseViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.Message
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import com.gmail.laktionov.a.r.guardianreader.domain.SingleArticleItem
import kotlinx.coroutines.channels.actor

class ArticleDetailsViewModel(private val repository: Repository,
                              private val articleData: MutableLiveData<SingleArticleItem> = MutableLiveData(),
                              private val messageData: MutableLiveData<Message> = MutableLiveData()) : BaseViewModel() {

    private val actionChannel = scope.actor<suspend () -> Unit>(capacity = 1) {
        for (event in channel) event.invoke()
    }

    private var currentArticleId: String = ""

    fun observeArticleData(): LiveData<SingleArticleItem> = articleData

    fun observeMessage(): LiveData<Message> = messageData

    fun getCurrentArticle(articleId: String) {
        actionChannel.offer {
            currentArticleId = articleId
            articleData.postValue(repository.getCurrentArticle(articleId))
        }
    }

    fun changePinState(isSelected: Boolean) {
        actionChannel.offer {
            val resultMessage = repository.changePinState(currentArticleId, isSelected)
            messageData.postValue(resultMessage)
        }
    }
}