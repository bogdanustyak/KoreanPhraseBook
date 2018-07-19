package com.leoart.koreanphrasebook.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R

/**
 * SearchResultsAdapter
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchResultsAdapter(private var items: MutableList<SectionOrRow>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_search_section -> {
                SectionViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.item_search_section, parent, false
                        )
                )
            }
            R.layout.item_search_result -> {
                SearchResultViewHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_search_result, parent, false))
            }
            else -> {
                throw IllegalArgumentException("No such view type!")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item.isRow) {
            if (holder is SearchResultViewHolder) {
                holder.tvTitle.text = item.row
            }
        } else {
            if (holder is SectionViewHolder) {
                holder.tvSectionTitle.text = item.section
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)
        val item = items[position]
        return if (!item.isRow) {
            R.layout.item_search_section
        } else {
            R.layout.item_search_result
        }
    }

    fun insert(items: ArrayList<SectionOrRow>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun append(items: List<SectionOrRow>) {
        this.items.addAll(items)
        notifyItemRangeChanged(itemCount, items.size)
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSectionTitle: TextView = itemView.findViewById(R.id.tv_title)
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}