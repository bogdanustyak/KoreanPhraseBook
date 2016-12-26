package com.leoart.koreanphrasebook.ui.vocabulary

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class VocabularyFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_vocabulary, container, false)

        DictionaryRequest().getDictionary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Dictionary>() {
                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun onNext(t: Dictionary?) {

                        Log.d("TAG", t.toString())
                        if (t != null) {
                            val recyclerView = view.findViewById(R.id.rv_vocabulary) as RecyclerView
                            recyclerView.layoutManager = StickyHeaderLayoutManager()
                            val adapter = DictionaryAdapter(context, t)
                            recyclerView.adapter = adapter
                        }

                    }

                    override fun onCompleted() {

                    }

                })

        return view
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(): VocabularyFragment {
            val fragment = VocabularyFragment()
            return fragment
        }
    }

}
