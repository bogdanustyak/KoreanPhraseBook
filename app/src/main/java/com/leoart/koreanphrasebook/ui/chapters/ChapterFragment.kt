package com.leoart.koreanphrasebook.ui.chapters

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.KoreanPhrasebookApp
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManagerImpl
import com.leoart.koreanphrasebook.data.analytics.ScreenNavigator
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesFragment
import com.leoart.koreanphrasebook.ui.models.Chapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ChapterFragment : BaseFragment(), ChaptersView,
        ChaptersRecyclerAdapter.ChaptersInteractionListener {

    private var mainView: MainView? = null
    private val chapters: List<Chapter>? = null
    private var adapter: ChaptersRecyclerAdapter? = null
    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chapter, container, false)
        val rvChapters = view.findViewById<RecyclerView>(R.id.rv_chapters)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvChapters.layoutManager = layoutManager
        rvChapters.itemAnimator = DefaultItemAnimator()
        rvChapters.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        adapter = ChaptersRecyclerAdapter(chapters, this, context)
        rvChapters.adapter = adapter
        context?.let {
            ChaptersPresenter(this, it).requestChapters()
        }
        analyticsManager.onOpenScreen(ScreenNavigator.CHAPTERS_SCREEN.screenName)
        return view
    }

    override fun onChapterClick(chapter: Chapter) {
        mainView?.let {
            it.add(
                    CategoriesFragment.newInstance(chapter.name, chapter, mainView)
            )
            analyticsManager.openChapter(chapter.name)
        }
    }

    override fun showChapters(chapters: List<Chapter>?) {
        chapters?.let {
            adapter?.setChapters(it)
        }
    }

    companion object {

        fun newInstance(title: String, mainView: MainView?): ChapterFragment {
            val fragment = ChapterFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            fragment.title = title
            return fragment
        }
    }
}
