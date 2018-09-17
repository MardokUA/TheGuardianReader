package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem

class ArticleAdapter : PagedListAdapter<ArticleItem, ArticleViewHolder>(DIFF_CALLBACK) {

    enum class Type(val value: Int) { RAW(1), PINTRESS(2) }

    private var currentType: Type = Type.RAW

    override fun getItemViewType(position: Int): Int {
        return currentType.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return when (viewType) {
            Type.RAW.value -> RawViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_raw_item, parent, false))
            else -> PintressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_pintres_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle)
        }
    }

    fun swapAdapterType() {
        currentType = if (currentType == Type.RAW) Type.PINTRESS else Type.RAW
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