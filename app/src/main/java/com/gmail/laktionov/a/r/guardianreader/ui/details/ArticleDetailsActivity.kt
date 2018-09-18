package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.doOnPreDraw
import com.gmail.laktionov.a.r.guardianreader.core.isLolipop
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*


class ArticleDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isLolipop()) initAnimation()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_article_details)

        viewModel = obtainViewModel(ArticleDetailsViewModel::class.java)
        viewModel.getCurrentArticle(getArticleIdFromIntent())

        setupObservers()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initAnimation() {
        window.apply {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            sharedElementEnterTransition = ChangeBounds()
        }
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
            Picasso.get().load(it.image).into(detailsImage, object : Callback {
                override fun onError(e: Exception?) {}
                override fun onSuccess() {
                    if (this@ArticleDetailsActivity.isLolipop()) {
                        detailsImage.doOnPreDraw {
                            detailsTitle.visibility = View.VISIBLE
                            startPostponedEnterTransition()
                        }
                    }
                }
            })
        }
    }

    override fun onBackPressed() {
        this.supportFinishAfterTransition()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleStartPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
                        startPostponedEnterTransition()
                        return true
                    }
                })
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