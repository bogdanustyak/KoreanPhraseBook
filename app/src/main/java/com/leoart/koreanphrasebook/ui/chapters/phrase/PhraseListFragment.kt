package com.leoart.koreanphrasebook.ui.chapters.phrase

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhrasesAdapter.OnPhrasesAdapterInteractionListener
import com.leoart.koreanphrasebook.ui.models.Phrase

/**
 * Created by bogdan on 6/18/17.
 */
class PhraseListFragment(title: String) : BaseFragment(title), PhrasesView, OnPhrasesAdapterInteractionListener {

    var category = ""
    private var adapter: PhrasesAdapter? = null
    var phrasePresenter: PhrasesPresenter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_phrase, container, false)

        val rvPhrases = view.findViewById<RecyclerView>(R.id.rv_phrases)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPhrases.layoutManager = layoutManager
        rvPhrases.itemAnimator = DefaultItemAnimator()

        adapter = PhrasesAdapter(emptyList<Phrase>(), this)
        rvPhrases.adapter = adapter

        phrasePresenter = PhrasesPresenter(
                this,
                category
        )
        phrasePresenter?.requestPhrases()

        return view
    }

    override fun showPhrases(phrases: List<Phrase>) {
        adapter?.updatePhrases(phrases)
    }

    override fun onFavouriteClicked(position: Int) {
        phrasePresenter?.onFavouriteClicked(position)
    }

    override fun updatePhrase(position: Int, phrase: Phrase?) {
        adapter?.notifyItemChanged(position, phrase)
    }

    companion object {

        fun newInstance(title: String, category: String): PhraseListFragment {
            val fragment = PhraseListFragment(title)
            val args = Bundle()
            fragment.arguments = args
            fragment.category = category
            return fragment
        }
    }
}