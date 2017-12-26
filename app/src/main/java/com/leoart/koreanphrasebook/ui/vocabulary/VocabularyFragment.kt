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
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import org.zakariya.stickyheaders.StickyHeaderLayoutManager


@SuppressLint("ValidFragment")
class VocabularyFragment(title: String) : BaseFragment(title) {

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mParam1 = arguments?.getString(ARG_PARAM1)
        mParam2 = arguments?.getString(ARG_PARAM2)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vocabulary, container, false)
        val model: DictionaryViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(DictionaryViewModel::class.java)
        model.geDictionary().observe(this, Observer<Dictionary> {
            it?.let {
                Log.d("TAG", it.toString())
                setDataInAdapter(it, view)
            }
        })
        return view
    }

    private fun setDataInAdapter(t: Dictionary, view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_vocabulary)
        val adapter = DictionaryAdapter(t)
        recyclerView.layoutManager = StickyHeaderLayoutManager()
        recyclerView.adapter = adapter

        val fastScrollingRecycler = view.findViewById<FastScrollRecyclerView>(R.id.recycler)
        fastScrollingRecycler.layoutManager = LinearLayoutManager(activity)
        //val letters = t.sort().keys.toTypedArray<Char>()
        //  fastScrollingRecycler.adapter = FastScrollingAdapter(letters)
    }

    private fun setupFastScrolling(data: Dictionary, view: View) {
        val fastScrollingRecycler = view.findViewById<FastScrollRecyclerView>(R.id.recycler)
        fastScrollingRecycler.layoutManager = LinearLayoutManager(activity)
        //  val letters = data.sort().keys.toTypedArray<Char>()
        // fastScrollingRecycler.adapter = FastScrollingAdapter(letters)
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
