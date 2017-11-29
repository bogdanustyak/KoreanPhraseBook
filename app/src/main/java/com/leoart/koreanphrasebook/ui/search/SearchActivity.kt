package com.leoart.koreanphrasebook.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.search.FBSearch
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        handleIntent(intent)

        val rvSearchResults = findViewById<RecyclerView>(R.id.rv_search_results)
        adapter = SearchResultsAdapter(emptyList())
        rvSearchResults.adapter = adapter
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            searchQuery(query)
        }
    }

    private fun searchQuery(query: String) {
        FBSearch(query).search()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    adapter.insert(items)
                })
    }
}
