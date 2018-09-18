package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem
import kotlinx.android.synthetic.main.view_article_pined_item.view.*

class PinedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: PinedItem) = with(itemView) {
        pinedTitle.text = item.title
        pinedSection.text = item.section
    }
}