package com.leoart.koreanphrasebook.ui.vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import org.zakariya.stickyheaders.SectioningAdapter
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class DictionaryAdapter(dictionary: Dictionary) : SectioningAdapter(), FastScrollRecyclerView.SectionedAdapter {

    private var list: Map<Char, List<HashMap<String, String>>>?
    private var letters: Array<Char>?
    private var size = 0

    init {
        this.list = dictionary.sortedData()
        this.letters = list?.keys?.toTypedArray()
        this.size = dictionary.totalCount()
    }

    override fun getNumberOfSections(): Int {
        letters?.let {
            return it.size
        }
        return 0
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        var count = 0
        list?.let { lt ->
            letters?.let { ltr ->
                count = lt[ltr[sectionIndex]]?.size ?: 0
            }
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
            letters?.let { ltr ->
                val s = ltr[sectionIndex]
                list?.let {
                    val item = it[s]?.get(itemIndex)
                    if (item != null) {
                        val text = item["word"] + " - " + item["translation"]
                        (viewHolder as DictViewHolder)
                                .text.text = text
                    }

                }
            }
        }
    }

    override fun onCreateGhostHeaderViewHolder(parent: ViewGroup): SectioningAdapter.GhostHeaderViewHolder {
        val ghostView = View(parent.context)
        ghostView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return SectioningAdapter.GhostHeaderViewHolder(ghostView)
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
        letters?.let {
            if (it.size >= position)
                return it[position].toString()
        }
        return ""
    }

    class DictViewHolder internal constructor(itemView: View) : SectioningAdapter.ItemViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.text)
    }

    fun setData(dictionary: Dictionary) {
        this.list = dictionary.sortedData()
        this.letters = list?.keys?.toTypedArray()
        this.size = dictionary.totalCount()
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder internal constructor(itemView: View) : SectioningAdapter.HeaderViewHolder(itemView) {
        var charHeader = itemView.findViewById<TextView>(R.id.charHeader)
    }
}
