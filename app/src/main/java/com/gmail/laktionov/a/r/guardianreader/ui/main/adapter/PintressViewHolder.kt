package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.view.View
import android.view.ViewTreeObserver
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_article_pintres_item.view.*

class PintressViewHolder(itemView: View) : ArticleViewHolder(itemView) {

    override fun bind(item: ArticleItem,
                      clickHandler: ((String) -> Unit)?) = with(itemView) {
        articleTitle.text = item.text
        articleSection.text = item.section

        clickHandler?.let { handler -> itemView.setOnClickListener { handler(item.articleId) } }

        if (item.image.isNotEmpty()) {
            Picasso.get().cancelRequest(articleImage)
            articleImage.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    articleImage.viewTreeObserver.removeOnPreDrawListener(this)
                    Picasso.get()
                            .load(item.image)
                            .resize(articleImage.width, articleImage.height)
                            .centerCrop()
                            .into(articleImage)
                    return true
                }
            })
        }
    }
}