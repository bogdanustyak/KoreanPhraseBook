package com.leoart.koreanphrasebook.data.repository.search

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.search.DictType
import com.leoart.koreanphrasebook.data.network.firebase.search.SearchResult
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteModel
import com.leoart.koreanphrasebook.data.repository.AppDataBase
import com.leoart.koreanphrasebook.ui.search.SearchResultsAdapter
import io.reactivex.Flowable
import io.reactivex.functions.Function5
import java.util.*


/**
 * SearchRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchRepository(val context: Context) : Search {

    override fun search(query: String): Flowable<List<SearchResult>> {
        val searchQuery = "%$query%"
        return Flowable.combineLatest(
                searchDictionary(searchQuery),
                searchDialogs(searchQuery),
                searchChapters(searchQuery),
                searchReplic(searchQuery),
                searchPhrase(searchQuery),
                Function5 { l1, l2, l3, l4, l5 ->
                    val list = ArrayList<SearchResult>()
                    list.addAll(l1)
                    list.addAll(l2)
                    list.addAll(l3)
                    list.addAll(l4)
                    list.addAll(l5)
                    return@Function5 list
                }
        )
    }

    fun searchChapters(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().chaptersDao().findByName(searchQuery)
                .toFlowable()
                .flatMap { chapters ->
                    val searchResults = chapters.map {
                        SearchResult("Path", it.name, DictType.CHAPTERS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchDialogs(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().dialogDao().findByName(searchQuery)
                .toFlowable()
                .flatMap { dialogs ->
                    val searchResults = dialogs.map {
                        SearchResult("Path", it.dialogTitle, DictType.DIALOGS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchDictionary(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().dictionaryDao().findBy(searchQuery)
                .toFlowable()
                .flatMap { dict ->
                    val searchResults = ArrayList<SearchResult>()
                    dict.forEach {
                        val title = it.word + " " + it.definition
                        searchResults.add(SearchResult("Path", title, DictType.DICTIONARY))
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchReplic(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().replicsDao().findByReplic(searchQuery)
                .toFlowable()
                .flatMap { replics ->
                    val searchResults = replics.map {
                        SearchResult("Path", it.ukrainian, DictType.REPLICS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchPhrase(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().phraseDao().findBy(searchQuery)
                .toFlowable()
                .flatMap { replics ->
                    val searchResults = replics.map {
                        SearchResult("Path", it.word + " " + it.translation + " " + it.transcription, DictType.REPLICS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun getDB(): AppDataBase {
        return AppDataBase.getInstance(context)
    }
}