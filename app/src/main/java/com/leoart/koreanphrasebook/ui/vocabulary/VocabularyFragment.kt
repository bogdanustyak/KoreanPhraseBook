package com.leoart.koreanphrasebook.ui.vocabulary

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.NoNetworkFragment
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.utils.NetworkChecker
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import javax.inject.Inject

class VocabularyFragment : BaseFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var recyclerViewVocabulary: RecyclerView? = null
    private val stickyHeaderLayoutManager = StickyHeaderLayoutManager()

    private lateinit var adapter: DictionaryAdapter
    private lateinit var model: DictionaryViewModel

    private var mainView: MainView? = null

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsManager.onOpenScreen(ScreenNavigator.DICTIONARY_SCREEN.screenName)
        mParam1 = arguments?.getString(ARG_PARAM1)
        mParam2 = arguments?.getString(ARG_PARAM2)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainView = (context as MainView)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vocabulary, container, false)
        model = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(DictionaryViewModel::class.java)
        initAdapter(view)
        model.geDictionary().observe(this, Observer<Dictionary> {
            it?.let {
                    setDataInAdapter(it, view)
            }

        })
        return view
    }

    override fun onResume() {
        super.onResume()
        (context as MainView).setTitle(getString(R.string.vocabulary))
    }

    override fun initToolbar() {
        mainView?.hideBackArrow()
    }

    private fun initAdapter(view: View) {
        recyclerViewVocabulary = view.findViewById(R.id.rv_vocabulary)
        recyclerViewVocabulary?.layoutManager = stickyHeaderLayoutManager
        adapter = DictionaryAdapter(Dictionary())
        adapter.setFavoriteClickListener(object : OnFavoriteClickListener {
            override fun onFavoriteCLick(position: Int) {
                val word = adapter.getDictionaryByPosition(position)
                word?.let {
                    //                    model.onFavouriteClicked(it)
                }
            }
        })
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
        val letters = data.data().keys.toTypedArray()
        fastScrollingRecycler.adapter = FastScrollingAdapter(
                letters,
                FastScrollingAdapter.FastScrollingAdapterInteractionListener {
                    val position = adapter.getAdapterPositionForSectionHeader(it)
                    stickyHeaderLayoutManager.scrollToPosition(position)
                }
        )
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(): VocabularyFragment {
            val fragment = VocabularyFragment()
            return fragment
        }

        interface OnFavoriteClickListener {
            fun onFavoriteCLick(position: Int)
        }
    }

}
