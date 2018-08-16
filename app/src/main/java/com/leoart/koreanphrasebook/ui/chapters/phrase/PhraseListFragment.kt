package com.leoart.koreanphrasebook.ui.chapters.phrase

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhrasesAdapter.OnPhrasesAdapterInteractionListener

/**
 * Created by bogdan on 6/18/17.
 */
class PhraseListFragment : BaseFragment(), OnPhrasesAdapterInteractionListener {

    var category = ""
    private lateinit var model: PhraseViewModel
    private var adapter: PhrasesAdapter? = null
    private lateinit var mainView: MainView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_phrase, container, false)
        val rvPhrases = view.findViewById<RecyclerView>(R.id.rv_phrases)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPhrases.layoutManager = layoutManager
        rvPhrases.itemAnimator = DefaultItemAnimator()

        adapter = PhrasesAdapter(emptyList<EPhrase>(), this)
        rvPhrases.adapter = adapter

        model = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(PhraseViewModel::class.java)
        model.getPhrases(category).observe(this, Observer<List<EPhrase>> {
            it?.let {
                Log.d("TAG", it.toString())
                adapter?.updatePhrases(it)
            }
        })
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainView = context as MainView
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    override fun initToolbar() {
        mainView.showBackArrow()
    }

    override fun onFavouriteClicked(position: Int) {
        adapter?.getPhraseByPosition(position)?.let {
            model.onFavouriteClicked(it)
        }
    }

    companion object {

        fun newInstance(title: String, category: String): PhraseListFragment {
            val fragment = PhraseListFragment()
            val args = Bundle()
            args.putString(MainActivity.TITLE, title)
            fragment.arguments = args
            fragment.category = category
            return fragment
        }
    }
}
