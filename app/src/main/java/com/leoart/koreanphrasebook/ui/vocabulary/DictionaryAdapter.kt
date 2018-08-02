package com.leoart.koreanphrasebook.ui.vocabulary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import org.zakariya.stickyheaders.SectioningAdapter
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */


//TODO create method to update single item on favourite state change ? ? ?
class DictionaryAdapter(dictionary: Dictionary) : SectioningAdapter(), FastScrollRecyclerView.SectionedAdapter {

    private var list: Map<Char, List<HashMap<String, String>>>?
    private var letters: Array<Char>?
    private var size = 0
    private var favoriteClickLisener: VocabularyFragment.Companion.OnFavoriteClickListener? = null

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
        list?.let { itList ->
            letters?.let { itLetters ->
                count = itList[itLetters[sectionIndex]]?.size ?: 0
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
        val vh = DictViewHolder(inflater.inflate(R.layout.dict_item, parent, false))
//        vh.icFavourite.setOnClickListener {
//            favoriteClickLisener?.onFavoriteCLick(vh.adapterPosition)
//        }
        return vh
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?, headerType: Int): HeaderViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.dict_header, parent, false)
        return HeaderViewHolder(v)
    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int, itemIndex: Int, itemType: Int) {
        if (list != null && letters != null) {
            letters?.let { itLetters ->
                val s = itLetters[sectionIndex]
                list?.let { itList ->
                    val item = itList[s]?.get(itemIndex)
                    if (item != null) {
                        val text = item["word"] + " - " + item["translation"]
                        (viewHolder as DictViewHolder)
                                .text.text = text
//                        viewHolder.icFavourite.setImageResource(getFavoriteResource(item["favourite"]))
                    }
                }
            }
        }
    }

    private fun getFavoriteResource(isFavourite: String?): Int {
        return if (isFavourite == "true") {
            R.drawable.ic_favorite_selected
        } else {
            R.drawable.ic_favorite_unselected
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
        val ghostHeadersCount = letters?.size ?: 0
        return size + ghostHeadersCount
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
//        var icFavourite = itemView.findViewById<ImageView>(R.id.ivFavourite)
    }

    fun setFavoriteClickListener(favoriteClickLisener: VocabularyFragment.Companion.OnFavoriteClickListener) {
        this.favoriteClickLisener = favoriteClickLisener
    }

    fun getDictionaryByPosition(position: Int): EDictionary? {
        var dictionary: EDictionary? = null
        val letterPosition = getSectionForAdapterPosition(position)
        val itemPositionInSection = getPositionOfItemInSection(letterPosition, position)
        var letter: Char? = null
        letters?.let {
            letter = it[letterPosition]
        }
        val section =
                letter?.let { itLetter ->
                    list?.let { itList ->
                        itList[itLetter]
                    }
                }
        val item = section?.get(itemPositionInSection)
        var isFavourite = "false"
        item?.let {
            it["favourite"]?.let { favourite ->
                isFavourite = favourite
            }
            dictionary = EDictionary(letter!!, it["word"]!!, it["translation"]!!, isFavourite)
        }
        return dictionary
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
