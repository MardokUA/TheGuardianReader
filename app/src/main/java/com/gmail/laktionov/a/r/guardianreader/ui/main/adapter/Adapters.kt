package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem


/**
 * Pined adapter
 */

class PinedAdapter(private val pinedList: MutableList<PinedItem> = mutableListOf()) :
        RecyclerView.Adapter<PinedViewHolder>() {

    private var clickHandler: ((String) -> Unit)? = null
    fun addClickListener(clickHandler: (String) -> Unit) = apply { this.clickHandler = clickHandler }

    fun updateList(newList: List<PinedItem>) {
        val result = DiffUtil.calculateDiff(PinedDiffCallback(pinedList, newList))
        result.dispatchUpdatesTo(this)
        pinedList.clear().also { pinedList.addAll(newList) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinedViewHolder {
        return PinedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_pined_item, parent, false))
    }

    override fun getItemCount(): Int = pinedList.size
    override fun onBindViewHolder(holder: PinedViewHolder, position: Int) {
        holder.bind(pinedList[position], clickHandler)
    }
}

/**
 * Pintress adapter
 */

class PintressAdapter : PagedListAdapter<ArticleItem, ArticleViewHolder>(DIFF_CALLBACK) {

    private var clickHandler: ((String, View) -> Unit)? = null
    fun addClickListener(clickHandler: (String, View) -> Unit) = apply { this.clickHandler = clickHandler }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return PintressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_pintres_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle, clickHandler)
        }
    }
}

/**
 * Raw adapter
 */

class RawAdapter : PagedListAdapter<ArticleItem, ArticleViewHolder>(DIFF_CALLBACK) {

    private var clickHandler: ((String, View) -> Unit)? = null
    fun addClickListener(clickHandler: (String, View) -> Unit) = apply { this.clickHandler = clickHandler }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return RawViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_article_raw_item, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentArticle = getItem(position)
        if (currentArticle != null) {
            holder.bind(currentArticle, clickHandler)
        }
    }
}

/**
 * Diff callbacks
 */

/**
 * Item
 */

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleItem>() {
    override fun areItemsTheSame(oldItem: ArticleItem?, newItem: ArticleItem?) =
            oldItem?.articleId == newItem?.articleId

    override fun areContentsTheSame(oldItem: ArticleItem?, newItem: ArticleItem?) =
            oldItem?.image == newItem?.image && oldItem?.title == newItem?.title && oldItem?.section == oldItem?.section
}

/**
 * List
 */

class PinedDiffCallback(private val oldList: List<PinedItem>,
                        private val newList: List<PinedItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].articleId == newList[newItemPosition].articleId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isSelected == newList[newItemPosition].isSelected
    }
}