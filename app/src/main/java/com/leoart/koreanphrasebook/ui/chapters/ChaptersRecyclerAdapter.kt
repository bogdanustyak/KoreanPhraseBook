package com.leoart.koreanphrasebook.ui.chapters

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.SimpleTextItemUI
import com.leoart.koreanphrasebook.ui.models.Chapter
import com.leoart.koreanphrasebook.utils.light
import org.jetbrains.anko.*

/**
 * Created by bogdan on 11/5/16.
 */
class ChaptersRecyclerAdapter(private var chapters: List<Chapter>?, private val interactionListener: ChaptersRecyclerAdapter.ChaptersInteractionListener?) : RecyclerView.Adapter<ChaptersRecyclerAdapter.ChapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val viewHolder = ChapterViewHolder(ChapterItemUI().createView(AnkoContext.create(parent.context, parent)))
        viewHolder.ll_chapter.setOnClickListener {
            interactionListener?.onChapterClick(chapters!![viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        chapters?.let {
            it[position].let {
                if (!TextUtils.isEmpty(it.name)) {
                    holder.tvChapterName.text = it.name
                } else {
                    holder.tvChapterName.text = ""
                }
            }
        }
    }

    fun setChapters(chapters: List<Chapter>) {
        this.chapters = chapters
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (chapters != null) {
            return chapters!!.size
        }
        return 0
    }

    class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ll_chapter = itemView.findViewById(R.id.ll_holder) as LinearLayout
        var tvChapterName = itemView.findViewById(R.id.tv_name) as TextView

    }

    interface ChaptersInteractionListener {
        fun onChapterClick(chapter: Chapter)
    }

    internal inner class ChapterItemUI : AnkoComponent<ViewGroup>, SimpleTextItemUI() {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    id = R.id.ll_holder
                    lparams(width = matchParent, height = dip(60))
                    orientation = LinearLayout.HORIZONTAL
                    padding = dip(10)
                    gravity = Gravity.CENTER_VERTICAL
                    imageView(R.mipmap.ic_launcher)
                            .lparams(width = wrapContent, height = wrapContent)
                    textView {
                        id = R.id.tv_name
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
