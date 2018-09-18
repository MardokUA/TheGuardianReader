package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        viewModel = obtainViewModel(ArticleDetailsViewModel::class.java)
        viewModel.getCurrentArticle(getArticleIdFromIntent())

        setupObservers()
    }

    private fun getArticleIdFromIntent(): String {
        return intent.getStringExtra(ARTICLE_ID)
    }

    private fun setupObservers() {
        viewModel.observeArticleData().observe(this,
                Observer { data -> data?.let { showContent(it) } })
    }

    private fun showContent(it: ArticleItem) {
        detailsContent.text = it.text
        detailsTitle.text = it.title
        if (it.image.isNotEmpty()) {
            Picasso.get().load(it.image).into(detailsImage)
        }
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