package com.leoart.koreanphrasebook.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.search.SearchResult

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchResultsAdapter(private var items: List<SearchResult>)
    : RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder>() {

    override fun onBindViewHolder(holder: SearchResultViewHolder?, position: Int) {
        val item = items[position]
        holder?.tvTitle?.text = item.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_result, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun insert(items: List<SearchResult>) {
        this.items = items
        notifyDataSetChanged()
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    }
}