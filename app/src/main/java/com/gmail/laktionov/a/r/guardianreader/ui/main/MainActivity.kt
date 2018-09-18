package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.Window
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.isLolipop
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.gmail.laktionov.a.r.guardianreader.ui.details.ArticleDetailsActivity
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.PinedAdapter
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.PintressAdapter
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.RawAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val rawAdapter by lazy { RawAdapter().addClickListener { id, view -> showContent(id, view) } }
    private val pintressAdapter by lazy { PintressAdapter().addClickListener { id, view -> showContent(id, view) } }
    private val pinedAdapter by lazy { PinedAdapter().addClickListener { id, view -> showContent(id, view) } }

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
                mainAllArticleRv.layoutManager = GridLayoutManager(this, 2)
                mainAllArticleRv.adapter = pintressAdapter
                pintressAdapter.submitList(viewModel.getArticles())
            } else {
                mainAllArticleRv.layoutManager = LinearLayoutManager(this)
                mainAllArticleRv.adapter = rawAdapter
                rawAdapter.submitList(viewModel.getArticles())
            }
        }

        with(mainAllArticleRv) {
            adapter = rawAdapter
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
            mainPinedRv.visibility = View.GONE
        } else {
            mainPinedRv.visibility = View.VISIBLE
            pinedAdapter.updateList(data)
        }
    }

    private fun showData(data: PagedList<ArticleItem>) {
        if (mainAllArticleRv.adapter is RawAdapter) {
            rawAdapter.submitList(data)
        } else {
            pintressAdapter.submitList(data)
        }
    }

    private fun showContent(articleId: String, view: View) {
        startActivity(ArticleDetailsActivity.createIntent(this, articleId),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "target_image").toBundle())
    }
}
