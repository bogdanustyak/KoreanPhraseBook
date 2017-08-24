package com.leoart.koreanphrasebook.ui.vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import org.zakariya.stickyheaders.SectioningAdapter
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class DictionaryAdapter(dictionary: Dictionary) : SectioningAdapter(), FastScrollRecyclerView.SectionedAdapter {

    private val list: SortedMap<Char, List<HashMap<String, String>>>?
    private val letters: Array<Char>?
    private var size = 0

    init {
        this.list = dictionary.sortedData()
        this.letters = list.keys.toTypedArray()
        this.size = dictionary.totalCount()
    }

    override fun getNumberOfSections(): Int {
        if (letters == null) {
            return 0
        }
        return letters.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        var count = 0
        if (list != null && letters != null) {
            count = list[letters[sectionIndex]]?.size ?: 0
        }
        return count
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, itemType: Int): DictViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.dict_item, parent, false)
        return DictViewHolder(v)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?, headerType: Int): HeaderViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.dict_header, parent, false)
        return HeaderViewHolder(v)
    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int, itemIndex: Int, itemType: Int) {
        if (list != null && letters != null) {
            val s = letters[sectionIndex]
            val item = list[s]?.get(itemIndex)
            if (item != null) {
                val text = item["word"] + " - " + item["translation"]
                (viewHolder as DictViewHolder)
                        .text.text = text
            }
        }
    }

    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder?, sectionIndex: Int, headerType: Int) {
        val s = letters!![sectionIndex]
        (viewHolder as HeaderViewHolder)
                .charHeader.text = s.toString()
    }


    override fun getItemCount(): Int {
        return size
    }

    override fun getSectionName(position: Int): String {
        if (letters == null || letters.size < position) {
            return ""
        }
        return letters[position].toString()
    }

    class DictViewHolder internal constructor(itemView: View) : SectioningAdapter.ItemViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text)
    }

    inner class HeaderViewHolder internal constructor(itemView: View) : SectioningAdapter.HeaderViewHolder(itemView) {
        var charHeader = itemView.findViewById<TextView>(R.id.charHeader)
    }
}
