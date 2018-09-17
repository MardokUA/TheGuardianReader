package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem

class PintressAdapter : PagedListAdapter<ArticleItem, ArticleViewHolder>(RawAdapter.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return PintressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_pintres_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle)
        }
    }
}