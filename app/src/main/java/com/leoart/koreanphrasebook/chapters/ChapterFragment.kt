package com.leoart.koreanphrasebook.chapters

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.chapters.category.CategoryScreenActivity
import com.leoart.koreanphrasebook.chapters.models.Chapter


class ChapterFragment : Fragment(), ChaptersView, ChaptersRecyclerAdapter.ChaptersInteractionListener {

    private val chapters: List<Chapter>? = null
    private var adapter: ChaptersRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_chapter, container, false)

        val rvChapters = view.findViewById(R.id.rv_chapters) as RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvChapters.layoutManager = layoutManager
        rvChapters.itemAnimator = DefaultItemAnimator()

        adapter = ChaptersRecyclerAdapter(chapters, this)
        rvChapters.adapter = adapter

        ChaptersPresenter(this).requestChapters()

        return view
    }

    override fun onChapterClick(chapter: Chapter) {
        val intent = Intent(activity, CategoryScreenActivity::class.java)
        //intent.putExtra(CategoryScreenActivity.CHAPTER_NAME, chapter.name)
        intent.putExtra(CategoryScreenActivity.CHAPTER_NAME, chapter)
        startActivity(intent)
    }

    override fun showChapters(chapters: List<Chapter>?) {
        adapter?.setChapters(chapters)
    }

    companion object {

        fun newInstance(): ChapterFragment {
            val fragment = ChapterFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
