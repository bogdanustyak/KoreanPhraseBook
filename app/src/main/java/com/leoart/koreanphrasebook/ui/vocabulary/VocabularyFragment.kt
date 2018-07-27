package com.leoart.koreanphrasebook.ui.vocabulary

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.KoreanPhrasebookApp
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManagerImpl
import com.leoart.koreanphrasebook.data.analytics.ScreenNavigator
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import javax.inject.Inject


@SuppressLint("ValidFragment")
class VocabularyFragment(title: String) : BaseFragment(title) {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var recyclerViewVocabulary: RecyclerView? = null
    private val stickyHeaderLayoutManager = StickyHeaderLayoutManager()

    private lateinit var adapter: DictionaryAdapter
    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAnalytics()
        mParam1 = arguments?.getString(ARG_PARAM1)
        mParam2 = arguments?.getString(ARG_PARAM2)
    }

    private fun initAnalytics() {
        (activity?.application as KoreanPhrasebookApp).analyticsComponent.inject(this)
        analyticsManager.onOpenScreen(ScreenNavigator.DICTIONARY_SCREEN.screenName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vocabulary, container, false)
        val model: DictionaryViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(DictionaryViewModel::class.java)
        initAdapter(view)
        model.geDictionary().observe(this, Observer<Dictionary> {
            it?.let {
                Log.d("TAG", it.toString())
                setDataInAdapter(it, view)
            }
        })
        return view
    }

    private fun initAdapter(view: View) {
        recyclerViewVocabulary = view.findViewById<RecyclerView>(R.id.rv_vocabulary)
        recyclerViewVocabulary?.layoutManager = stickyHeaderLayoutManager
        adapter = DictionaryAdapter(Dictionary())
        recyclerViewVocabulary?.adapter = adapter
    }

    private fun setDataInAdapter(t: Dictionary, view: View) {
        adapter.setData(t)
        setupFastScrolling(t, view)
    }

    private fun setupFastScrolling(data: Dictionary, view: View) {
        val fastScrollingRecycler = view.findViewById<FastScrollRecyclerView>(R.id.recycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        fastScrollingRecycler.layoutManager = linearLayoutManager
        val letters = data.sortedData().keys.toTypedArray()
        fastScrollingRecycler.adapter = FastScrollingAdapter(
                letters,
                FastScrollingAdapter.FastScrollingAdapterInteractionListener {
                    val position = data.positionOf(it)
                    stickyHeaderLayoutManager.scrollToPosition(position)
                }
        )
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(title: String): VocabularyFragment {
            val fragment = VocabularyFragment(title)
            return fragment
        }
    }

}
