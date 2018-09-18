package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.laktionov.a.r.guardianreader.R
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem

class PinedAdapter(private val pinedList: MutableList<PinedItem> = mutableListOf()) :
        RecyclerView.Adapter<PinedViewHolder>() {

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
        holder.bind(pinedList[position])
    }
}