package com.leoart.koreanphrasebook.ui.search

import android.support.v7.util.DiffUtil


/**
 * Created by Maryan Onysko (maryan.onysko@gmail.com)
 */

class SearchDiffUtilCallback(private val oldList: List<SectionOrRow>,
                             private val newList: List<SectionOrRow>) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isRow != newList[newItemPosition].isRow
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldList[oldItemPosition].isRow) {
            oldList[oldItemPosition].row == newList[newItemPosition].row
        } else {
            oldList[oldItemPosition].section == newList[newItemPosition].section
        }
    }

}