package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.view.View
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_article_pintres_item.view.*

class PintressViewHolder(itemView: View) : ArticleViewHolder(itemView) {

    override fun bind(item: ArticleItem) = with(itemView) {
        articleTitle.text = item.text
        articleSection.text = item.section

        if (item.image.isNotEmpty()) {
            Picasso.get().cancelRequest(articleImage)
            Picasso.get().load(item.image).into(articleImage)
        }
    }
}