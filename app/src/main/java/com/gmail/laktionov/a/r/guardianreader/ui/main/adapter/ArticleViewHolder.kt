package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem

abstract class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: ArticleItem)
}