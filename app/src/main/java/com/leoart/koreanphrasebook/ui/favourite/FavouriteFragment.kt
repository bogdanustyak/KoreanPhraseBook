package com.leoart.koreanphrasebook.ui.favourite

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
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteModel
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_phrase.*
import android.view.ViewStub


/**
 * Created by bogdan on 6/18/17.
 */
class FavouriteFragment : BaseFragment() {

    private var adapter: FavouriteAdapter? = null

    private lateinit var model: FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_phrase, container, false)
        val rvPhrases = view.findViewById<RecyclerView>(R.id.rv_phrases)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rvPhrases.layoutManager = layoutManager
        rvPhrases.itemAnimator = DefaultItemAnimator()

        adapter = FavouriteAdapter(emptyList<FavouriteModel>(), object : OnFavouriteClickListener {
            override fun onFavouriteClick(position: Int) {
                adapter?.getItemByPosition(position)?.let {
                    model.onFavouriteClicked(it)
                }
            }
        })
        rvPhrases.adapter = adapter

        model = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(FavouriteViewModel::class.java)
        model.getData().observe(this, Observer<List<FavouriteModel>> {
            it?.let {
                if (it.isEmpty()) {
                    stubView.layoutResource = R.layout.no_favourite_screen
                    stubView.inflate()
                } else {
                    adapter?.updatePhrases(it)
                }
            }
        })

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as MainView).setTitle(getString(R.string.menu_favourite))
    }

    companion object {

        fun newInstance(): FavouriteFragment {
            val fragment = FavouriteFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        interface OnFavouriteClickListener {
            fun onFavouriteClick(position: Int)
        }
    }
}