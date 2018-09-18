package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel

class ArticleDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        viewModel = obtainViewModel(ArticleDetailsViewModel::class.java)

        setupObservers()
    }

    private fun setupObservers() {

    }

    companion object {

        private const val ARTICLE_ID = "article_id"

        fun createIntent(context: Context, articleId: String): Intent {
            return Intent(context, ArticleDetailsActivity::class.java).also {
                it.putExtra(ARTICLE_ID, articleId)
            }
        }
    }
}