package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.isPreLolipop
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.gmail.laktionov.a.r.guardianreader.ui.details.ArticleDetailsActivity
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.ArticlesAdapter
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.PinedAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val pinedAdapter by lazy { PinedAdapter().addClickListener { id -> showContent(id) } }
    private val articleAdapter by lazy { ArticlesAdapter().addClickListener { id, view -> showContentWithTransition(id, view) } }

    init {
        if (isPreLolipop()) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = obtainViewModel(MainViewModel::class.java)

        setupView()
        setupObservers()
    }

    private fun setupView() {
        mainSwapViewButton.setOnClickListener { view ->
            view.isSelected = !view.isSelected
            if (view.isSelected) {
                mainAllArticleRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            } else {
                mainAllArticleRv.layoutManager = LinearLayoutManager(this)
            }
            articleAdapter.swapViewType()
            articleAdapter.notifyDataSetChanged()
        }

        with(mainAllArticleRv) {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
        }

        with(mainPinedRv) {
            adapter = pinedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupObservers() {
        viewModel.observeArticles().observe(this,
                Observer { data -> data?.let { showData(it) } })
        viewModel.observePinnedArticles().observe(this,
                Observer { data -> showPinedData(data) })
    }

    private fun showPinedData(data: List<PinedItem>?) {
        if (data == null || data.isEmpty()) {
            mainPinedContainer.visibility = View.GONE
        } else {
            mainPinedContainer.visibility = View.VISIBLE
            pinedAdapter.updateList(data)
        }
    }

    private fun showData(data: PagedList<ArticleItem>) {
        articleAdapter.submitList(data)
    }

    private fun showContent(articleId: String) {
        startActivity(ArticleDetailsActivity.createIntent(this, articleId))
    }

    private fun showContentWithTransition(articleId: String, view: View) {
        startActivity(ArticleDetailsActivity.createIntent(this, articleId),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "target_image").toBundle())
    }
}
