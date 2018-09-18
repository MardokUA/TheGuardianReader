package com.gmail.laktionov.a.r.guardianreader.ui.main.adapter

import android.support.v7.util.DiffUtil
import com.gmail.laktionov.a.r.guardianreader.domain.PinedItem

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