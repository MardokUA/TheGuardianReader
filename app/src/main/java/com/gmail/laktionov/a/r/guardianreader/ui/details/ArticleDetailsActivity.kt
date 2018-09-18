package com.gmail.laktionov.a.r.guardianreader.ui.details

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.transition.ChangeBounds
import android.view.View
import android.view.Window
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.*
import com.gmail.laktionov.a.r.guardianreader.domain.Message
import com.gmail.laktionov.a.r.guardianreader.domain.SingleArticleItem
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_article_details.*
import kotlinx.android.synthetic.main.view_article_pintres_item.view.*

class ArticleDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleDetailsViewModel

    init {
        if (isPreLolipop()) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isLolipop()) initAnimation()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_article_details)

        viewModel = obtainViewModel(ArticleDetailsViewModel::class.java)
        viewModel.getCurrentArticle(getArticleIdFromIntent())

        setupView()
        setupObservers()
    }

    private fun setupView() {
        detailsPinButton.setOnClickListener {
            viewModel.changePinState(!it.isSelected)
        }
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
        viewModel.observeMessage().observe(this,
                Observer { isSuccess -> isSuccess?.let { showMessage(it) } })
    }

    private fun showContent(it: SingleArticleItem) {
        detailsContent.text = it.item.text
        detailsTitle.text = it.item.title
        detailsPinButton.isSelected = it.isSelected

        if(isPreLolipop()){
            detailsTitle.visibility = View.VISIBLE
            detailsPinButton.visibility = View.VISIBLE
        }

        if (it.item.image.isNotEmpty()) {
            Picasso.get().load(it.item.image).into(detailsImage, object : Callback {
                override fun onError(e: Exception?) {}
                override fun onSuccess() {
                    if (this@ArticleDetailsActivity.isLolipop()) {
                        detailsImage.doOnPreDraw {
                            detailsTitle.visibility = View.VISIBLE
                            detailsPinButton.visibility = View.VISIBLE
                            startPostponedEnterTransition()
                        }
                    }
                }
            })
        }else{
            detailsImage.setImageResource(R.mipmap.ic_app_launcher)
        }
    }

    private fun showMessage(message: Message) {
        detailsPinButton.isSelected = !detailsPinButton.isSelected
        val backgroundColor = if (message.isSuccess) android.R.color.holo_green_dark else android.R.color.holo_red_dark
        detailsContentContainer.showSnackbar(message.messageText, backgroundColor)
    }

    override fun onBackPressed() {
        this.supportFinishAfterTransition()
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