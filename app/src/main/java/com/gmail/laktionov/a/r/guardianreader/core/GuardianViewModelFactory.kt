package com.gmail.laktionov.a.r.guardianreader.core

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.gmail.laktionov.a.r.guardianreader.domain.Repository
import com.gmail.laktionov.a.r.guardianreader.ui.details.ArticleDetailsViewModel
import com.gmail.laktionov.a.r.guardianreader.ui.main.MainViewModel

/**
 * Simple custom factory. Provides dependencies to other part of architecture
 */
@Suppress("UNCHECKED_CAST")
class GuardianViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository)
                isAssignableFrom(ArticleDetailsViewModel::class.java) -> ArticleDetailsViewModel(repository)
                else -> throwError(modelClass)
            }
        } as T
    }

    private fun throwError(modelClass: Class<*>): Nothing {
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }


    companion object {

        lateinit var INSTANSE: GuardianViewModelFactory

        fun initialize(repository: Repository) {
            INSTANSE = GuardianViewModelFactory(repository)
        }
    }

}