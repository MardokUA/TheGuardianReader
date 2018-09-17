package com.gmail.laktionov.a.r.guardianreader.ui.main

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.obtainViewModel
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.PintressAdapter
import com.gmail.laktionov.a.r.guardianreader.ui.main.adapter.RawAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val rawAdapter by lazy { RawAdapter() }
    private val pintressAdapter by lazy { PintressAdapter() }

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
    }

    private fun setupObservers() {
        viewModel.observeArticles().observe(this,
                Observer { data -> data?.let { showData(it) } })
    }

    private fun showData(data: PagedList<ArticleItem>) {
        Log.w("MainActivity", "$data")
        if (mainAllArticleRv.adapter is RawAdapter) {
            rawAdapter.submitList(data)
        } else {
            pintressAdapter.submitList(data)
        }
    }
}
