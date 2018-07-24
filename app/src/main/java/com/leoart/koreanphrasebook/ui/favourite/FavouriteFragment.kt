package com.leoart.koreanphrasebook.ui.favourite

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.repository.AppDataBase
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhrasesAdapter
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhrasesAdapter.OnPhrasesAdapterInteractionListener
import com.leoart.koreanphrasebook.ui.models.Phrase

/**
 * Created by bogdan on 6/18/17.
 */
class FavouriteFragment : BaseFragment(), FavouriteView, OnPhrasesAdapterInteractionListener {

    private var mainView: MainView? = null
    private var adapter: PhrasesAdapter? = null
    private var presenter: FavouritePresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_phrase, container, false)


        val rvPhrases = view.findViewById<RecyclerView>(R.id.rv_phrases)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPhrases.layoutManager = layoutManager
        rvPhrases.itemAnimator = DefaultItemAnimator()

        //adapter = PhrasesAdapter(emptyList<EDictionary>(), this)
        rvPhrases.adapter = adapter
        context?.let {
            presenter = FavouritePresenter(this, AppDataBase.getInstance(it))
        }
        presenter?.requestPhrases()

        return view
    }

    override fun showPhrases(phrases: List<EDictionary>) {
     //   adapter?.updatePhrases(phrases)
    }

    override fun removePhrase(position: Int) {
        adapter?.notifyItemRemoved(position)
    }

    override fun onFavouriteClicked(position: Int) {
//        val item = adapter?.getItemByPosition(position)
//        item?.let { presenter?.onFavouriteClicked(position, it) }

    }

    companion object {

        fun newInstance(title: String, mainView: MainView?): FavouriteFragment {
            val fragment = FavouriteFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            fragment.title = title
            return fragment
        }
    }
}