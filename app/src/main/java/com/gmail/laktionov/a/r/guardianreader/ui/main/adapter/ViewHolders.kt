package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewTreeObserver
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_article_pined_item.view.*
import kotlinx.android.synthetic.main.view_article_pintres_item.view.*
import kotlinx.android.synthetic.main.view_article_raw_item.view.*

abstract class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ArticleItem, clickHandler: ((String) -> Unit)?)
}

/**
 * Raw holder
 */
class RawViewHolder(itemView: View) : ArticleViewHolder(itemView) {

    override fun bind(item: ArticleItem,
                      clickHandler: ((String) -> Unit)?) = with(itemView) {

        rawTitle.text = item.title
        rawSelection.text = item.section

        clickHandler?.let { handler -> itemView.setOnClickListener { handler(item.articleId) } }

        if (item.image.isNotEmpty()) {
            Picasso.get().cancelRequest(rawImage)
            Picasso.get().load(item.image).into(rawImage)
        }
    }
}

/**
 * Pintress holder
 */
class PintressViewHolder(itemView: View) : ArticleViewHolder(itemView) {

    override fun bind(item: ArticleItem,
                      clickHandler: ((String) -> Unit)?) = with(itemView) {
        pinrressTitle.text = item.text
        pintressSelection.text = item.section

        clickHandler?.let { handler -> itemView.setOnClickListener { handler(item.articleId) } }

        if (item.image.isNotEmpty()) {
            Picasso.get().cancelRequest(pintressImage)
            pintressImage.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    pintressImage.viewTreeObserver.removeOnPreDrawListener(this)
                    Picasso.get()
                            .load(item.image)
                            .resize(pintressImage.width, pintressImage.height)
                            .centerCrop()
                            .into(pintressImage)
                    return true
                }
            })
        }
    }
}

/**
 * Pinned holder
 */
class PinedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: PinedItem) = with(itemView) {
        pinedTitle.text = item.title
        pinedSection.text = item.section
    }
}