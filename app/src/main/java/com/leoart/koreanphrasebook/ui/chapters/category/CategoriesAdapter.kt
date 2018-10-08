package com.leoart.koreanphrasebook.ui.chapters.category

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.ui.SimpleTextItemUI
import com.leoart.koreanphrasebook.ui.models.Category
import org.jetbrains.anko.AnkoContext

/**
 * Created by bogdan on 11/5/16.
 */
class CategoriesAdapter(private var chapterCategories: List<ECategory>?, private val interactionListener: CategoriesAdapter.CategoryInteractionListener?) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val viewHolder = CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
              //  SimpleTextItemUI().createView(AnkoContext.create(parent.context, parent))
        )
        viewHolder.ll_chapter.setOnClickListener {
            if (interactionListener != null && chapterCategories != null) {
                interactionListener.onCategoryClick(chapterCategories!![viewHolder.adapterPosition])
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        chapterCategories?.let {
            it[position].let {
                holder.tvCategoryName.text = it.category
            }
        }
    }

    fun setCategories(categories: List<ECategory>) {
        this.chapterCategories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return chapterCategories?.size ?: 0
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ll_chapter = itemView.findViewById<LinearLayout>(R.id.ll_holder)
        var tvCategoryName = itemView.findViewById<TextView>(R.id.tv_name)
    }

    interface CategoryInteractionListener {
        fun onCategoryClick(category: ECategory)
    }
}
