package com.leoart.koreanphrasebook.ui.chapters

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.SimpleTextItemUI
import com.leoart.koreanphrasebook.ui.info.InfoItem
import org.jetbrains.anko.AnkoContext

/**
 * Created by bogdan on 11/5/16.
 */
class InfoRecyclerAdapter(private var items: List<InfoItem>?, private val interactionListener: InfoInteractionListener?) : RecyclerView.Adapter<InfoRecyclerAdapter.InfoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoItemViewHolder {
        val viewHolder = InfoItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
              //  SimpleTextItemUI().createView(AnkoContext.create(parent.context, parent))
        )
        viewHolder.llItemHolder.setOnClickListener {
            interactionListener?.onItemClick(items!![viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: InfoItemViewHolder, position: Int) {
        items?.let {
            it[position].let {
                if (!TextUtils.isEmpty(it.name)) {
                    holder.tvItemName.text = it.name
                } else {
                    holder.tvItemName.text = ""
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (items != null) {
            return items!!.size
        }
        return 0
    }

    class InfoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var llItemHolder = itemView.findViewById<LinearLayout>(R.id.ll_holder)
        var tvItemName = itemView.findViewById<TextView>(R.id.tv_name)

    }

    interface InfoInteractionListener {
        fun onItemClick(item: InfoItem)
    }
}