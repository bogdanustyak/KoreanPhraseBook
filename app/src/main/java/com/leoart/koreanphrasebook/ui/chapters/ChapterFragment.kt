package com.leoart.koreanphrasebook.ui.chapters

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesFragment
import com.leoart.koreanphrasebook.ui.chapters.models.Chapter


class ChapterFragment(title: String) : BaseFragment(title), ChaptersView, ChaptersRecyclerAdapter.ChaptersInteractionListener {

    private var mainView: MainView? = null
    private val chapters: List<Chapter>? = null
    private var adapter: ChaptersRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_chapter, container, false)

        val rvChapters = view.findViewById(R.id.rv_chapters) as RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvChapters.layoutManager = layoutManager
        rvChapters.itemAnimator = DefaultItemAnimator()
        rvChapters.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))


        adapter = ChaptersRecyclerAdapter(chapters, this)
        rvChapters.adapter = adapter

        ChaptersPresenter(this).requestChapters()

        return view
    }

    override fun onChapterClick(chapter: Chapter) {
        mainView?.let {
            it.add(
                    CategoriesFragment.newInstance(chapter.name, chapter)
            )
        }
    }

    override fun showChapters(chapters: List<Chapter>?) {
        adapter?.setChapters(chapters)
    }

    companion object {

        fun newInstance(title: String, mainView: MainView?): ChapterFragment {
            val fragment = ChapterFragment(title)
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}
