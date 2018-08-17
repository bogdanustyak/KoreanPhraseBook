package com.leoart.koreanphrasebook.ui.chapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.analytics.ScreenNavigator
import com.leoart.koreanphrasebook.data.repository.DataInfoRepository
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.NoNetworkFragment
import com.leoart.koreanphrasebook.ui.alphabet.AlphabetActivity
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesFragment
import com.leoart.koreanphrasebook.ui.models.Chapter
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ChapterFragment : BaseFragment(), ChaptersView,
        ChaptersRecyclerAdapter.ChaptersInteractionListener {

    private lateinit var mainView: MainView
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

    override fun onResume() {
        super.onResume()
        (context as MainView).setTitle(getString(R.string.chapters))
    }

    override fun onChapterClick(chapter: Chapter) {
        if (chapter.name == getString(R.string.alphabet_chapter_name)) {
            if (!mainView.isNetworkAvailable() && this.isNotSynced(ELetter::class.java.simpleName)) {
                mainView.replace(NoNetworkFragment.newInstance())
            } else {
                startActivity(Intent(context, AlphabetActivity::class.java))
            }
        } else {
            if (!mainView.isNetworkAvailable() && isNotSynced(ECategory::class.java.simpleName)) {
                mainView.replace(NoNetworkFragment.newInstance())
            } else {
                mainView.replace(
                        CategoriesFragment.newInstance(chapter.name, chapter, mainView)
                )
            }
        }
    }

    override fun initToolbar() {
        mainView.hideBackArrow()
    }

    private fun isNotSynced(indicator: String): Boolean {
        val syncInfo = DataInfoRepository.getInstance().getData()
        return syncInfo != null && syncInfo.contains(SyncModel(indicator, true))
    }

    override fun showChapters(chapters: List<Chapter>?) {
        activity?.let {
            chapters?.let { itChapters ->
                adapter?.setChapters(chapters)
            }
        }
    }

    companion object {

        fun newInstance(mainView: MainView): ChapterFragment {
            val fragment = ChapterFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}
