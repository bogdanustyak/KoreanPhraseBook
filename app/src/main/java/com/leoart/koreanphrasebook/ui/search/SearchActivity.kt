package com.leoart.koreanphrasebook.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.repository.search.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class SearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var adapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        handleIntent(intent)

        val rvSearchResults = findViewById<RecyclerView>(R.id.rv_search_results)
        adapter = SearchResultsAdapter(mutableListOf())
        rvSearchResults.adapter = adapter

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
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
        SearchRepository(this).search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchResult ->
                    if (searchResult.isNotEmpty()) {
                        val data = ArrayList<SectionOrRow>()
                        searchResult.groupBy { it.type }
                                .forEach {
                                    data.add(SectionOrRow.createSection(it.key.name))
                                    it.value.forEach {
                                        data.add(SectionOrRow.createRow(it.title))
                                    }
                                }
                        adapter.append(data)
                    }
                }, { throwable ->
                    throw UnsupportedOperationException("not implemented")
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as? SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setIconifiedByDefault(true)
        searchView?.isSubmitButtonEnabled = true
        intent.getStringExtra(SearchManager.QUERY)?.let {
            searchView?.setQuery(it, false)
        }
        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchQuery(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                onSearchRequested()
            }
        }
        return true
    }
}
