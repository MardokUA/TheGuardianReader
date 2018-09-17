package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem

class RawAdapter : PagedListAdapter<ArticleItem, ArticleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return RawViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_raw_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle)
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleItem>() {

            override fun areItemsTheSame(oldItem: ArticleItem?, newItem: ArticleItem?): Boolean {
                return oldItem?.articleId == newItem?.articleId
            }

            override fun areContentsTheSame(oldItem: ArticleItem?, newItem: ArticleItem?): Boolean {
                return oldItem == newItem
            }
        }
    }
}