package com.leoart.koreanphrasebook.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.widget.RxSearchView
import com.leoart.koreanphrasebook.KoreanPhrasebookApp
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManagerImpl
import com.leoart.koreanphrasebook.data.analytics.ScreenNavigator
import com.leoart.koreanphrasebook.data.network.firebase.search.DictType
import com.leoart.koreanphrasebook.data.repository.search.SearchRepository
import com.leoart.koreanphrasebook.ui.BaseActivity
import com.leoart.koreanphrasebook.utils.SoftKeyboard
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SearchActivity : BaseActivity() {

    private lateinit var adapter: SearchResultsAdapter
    private val compositeDisposable = CompositeDisposable()
    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        AndroidInjection.inject(this)
        analyticsManager.onOpenScreen(ScreenNavigator.SEARCH_SCREEN.screenName)
        initUI()
        handleIntent(intent)
    }

    private fun initUI() {
        setupSearchView()
        val rvSearchResults = findViewById<RecyclerView>(R.id.rv_search_results)
        adapter = SearchResultsAdapter(mutableListOf())
        rvSearchResults.adapter = adapter

        rvSearchResults?.setOnTouchListener { _, _ ->
            SoftKeyboard(this).hide()
            return@setOnTouchListener false
        }
        searchback.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // hint, inputType & ime options seem to be ignored from XML! Set in code
        searchView.queryHint = getString(R.string.search)
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN

        val inputSubscription = RxSearchView.queryTextChanges(searchView)
                .debounce(TIMEOUT, TimeUnit.MILLISECONDS)

        compositeDisposable.add(inputSubscription
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    clearResults()
                    if (it.length >= MIN_QUERY_LENTH)
                        searchQuery(it.toString())
                })
    }

    fun clearResults() {
        adapter.clear()
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (!query.isNullOrBlank()) {
                searchView.setQuery(query, false)
                searchView.requestFocus()
                searchQuery(query)
            }
        }
    }

    override fun onBackPressed() {
        this.finish()
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
                                    data.add(SectionOrRow.createSection(typeLocalisedTitle(it.key)))
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

    private fun typeLocalisedTitle(type: DictType): String {
        return when (type) {
            DictType.DIALOGS -> getString(R.string.menu_dialogs)
            DictType.CHAPTERS -> getString(R.string.menu_chapters)
            DictType.DICTIONARY -> getString(R.string.menu_dict)
            DictType.REPLICS -> getString(R.string.menu_chapters)
            else -> ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        const val TIMEOUT = 300L
        const val MIN_QUERY_LENTH = 3
    }

}
