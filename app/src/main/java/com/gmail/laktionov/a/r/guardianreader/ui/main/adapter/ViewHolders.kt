package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.core.doOnPreDraw
import com.gmail.laktionov.a.r.guardianreader.domain.ArticleItem
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_article_pined_item.view.*
import kotlinx.android.synthetic.main.view_article_pintres_item.view.*
import kotlinx.android.synthetic.main.view_article_raw_item.view.*

abstract class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ArticleItem, clickHandler: ((String, View) -> Unit)?)
}

/**
 * Raw holder
 */
class RawViewHolder(itemView: View) : ArticleViewHolder(itemView) {

    override fun bind(item: ArticleItem,
                      clickHandler: ((String, View) -> Unit)?) = with(itemView) {

        rawTitle.text = item.title
        rawSelection.text = item.section

        clickHandler?.let { handler -> itemView.setOnClickListener { handler(item.articleId, rawImage) } }

        if (item.image.isNotEmpty()) {
            Picasso.get().cancelRequest(rawImage)
            rawImage.doOnPreDraw {
                Picasso.get()
                        .load(item.image)
                        .resize(rawImage.width, rawImage.height)
                        .centerCrop()
                        .into(rawImage)
            }
        }
        else{
            rawImage.setImageResource(R.mipmap.ic_app_launcher)
        }
    }
}

/**
 * Pintress holder
 */
class PintressViewHolder(itemView: View) : ArticleViewHolder(itemView) {

    override fun bind(item: ArticleItem,
                      clickHandler: ((String, View) -> Unit)?) = with(itemView) {
        pinrressTitle.text = item.text
        pintressSelection.text = item.section

        clickHandler?.let { handler -> itemView.setOnClickListener { handler(item.articleId, pintressImage) } }

        if (item.image.isNotEmpty()) {
            Picasso.get().cancelRequest(pintressImage)
            pintressImage.doOnPreDraw {
                Picasso.get()
                        .load(item.image)
                        .into(pintressImage)
            }
        }else{
            pintressImage.setImageResource(R.mipmap.ic_app_launcher)
        }
    }
}

/**
 * Pinned holder
 */
class PinedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: PinedItem,
             clickHandler: ((String) -> Unit)?) = with(itemView) {
        pinedTitle.text = item.title
        pinedSection.text = item.section

        clickHandler?.let { handler -> itemView.setOnClickListener { handler(item.articleId) } }
    }
}