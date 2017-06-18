package com.leoart.koreanphrasebook.ui.chapters.category

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.SimpleTextItemUI
import com.leoart.koreanphrasebook.ui.chapters.models.Category
import com.leoart.koreanphrasebook.utils.light
import org.jetbrains.anko.*

/**
 * Created by bogdan on 11/5/16.
 */
class CategoriesAdapter(private var chapterCategories: List<Category>?, private val interactionListener: CategoriesAdapter.CategoryInteractionListener?) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        //val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        //val viewHolder = CategoryViewHolder(itemView)
        val viewHolder = CategoryViewHolder(CategoryItemUI().createView(AnkoContext.Companion.create(parent.context, parent)))
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
                holder.tvCategoryName.text = it.name["word"]
            }
        }
    }

    fun setCategories(categories: List<Category>) {
        this.chapterCategories = categories
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (chapterCategories != null) {
            return chapterCategories!!.size
        }
        return 0
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ll_chapter = itemView.findViewById(R.id.ll_chapter) as LinearLayout
        var tvCategoryName = itemView.findViewById(R.id.tv_category_name) as TextView

    }

    interface CategoryInteractionListener {
        fun onCategoryClick(category: Category)
    }

    internal inner class CategoryItemUI : AnkoComponent<ViewGroup>, SimpleTextItemUI() {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    id = R.id.ll_chapter
                    lparams(width = matchParent, height = dip(60))
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(10)
                    gravity = Gravity.CENTER_VERTICAL
                    imageView(R.mipmap.ic_launcher)
                            .lparams(width = wrapContent, height = wrapContent)
                    textView {
                        id = R.id.tv_category_name
                        text = "My light text"
                        textSize = 18f
                        typeface = light
                        textColor = R.color.list_text_color
                    }
                }
            }
        }
    }
}
