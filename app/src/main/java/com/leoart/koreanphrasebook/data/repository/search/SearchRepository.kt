package com.leoart.koreanphrasebook.data.repository.search

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.search.DictType
import com.leoart.koreanphrasebook.data.network.firebase.search.SearchResult
import com.leoart.koreanphrasebook.data.repository.AppDataBase
import io.reactivex.Flowable


/**
 * SearchRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchRepository(val context: Context) : Search {

    override fun search(query: String): Flowable<List<SearchResult>> {
        val searchQuery = "%$query%"
        return Flowable.merge(
                searchDictionary(searchQuery),
                searchDialogs(searchQuery),
                searchChapters(searchQuery),
                searchReplic(searchQuery)
        )
    }

    fun searchChapters(searchQuery: String): Flowable<List<SearchResult>> {
        return AppDataBase.getInstance(context).chaptersDao().findByName(searchQuery)
                .toFlowable()
                .flatMap { chapters ->
                    val searchResults = chapters.map {
                        SearchResult("Path", it.name, DictType.CHAPTERS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchDialogs(searchQuery: String): Flowable<List<SearchResult>> {
        return AppDataBase.getInstance(context).dialogDao().findByName(searchQuery)
                .toFlowable()
                .flatMap { dialogs ->
                    val searchResults = dialogs.map {
                        SearchResult("Path", it.dialogTitle, DictType.DIALOGS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchDictionary(searchQuery: String): Flowable<List<SearchResult>> {
        return AppDataBase.getInstance(context).dictionaryDao().findBy(searchQuery)
                .toFlowable()
                .flatMap { dict ->
                    val searchResults = ArrayList<SearchResult>()
                    dict.forEach {
                        var title = ""
                        if (it.definition.contains(searchQuery)) {
                            title = it.definition
                        } else if (it.word.contains(searchQuery)) {
                            title = it.word
                        }
                        searchResults.add(SearchResult("Path", title, DictType.DICTIONARY))
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchReplic(searchQuery: String): Flowable<List<SearchResult>> {
        return AppDataBase.getInstance(context).replicsDao().findByReplic(searchQuery)
                .toFlowable()
                .flatMap { replics ->
                    val searchResults = replics.map {
                        SearchResult("Path", it.ukrainian, DictType.REPLICS)
                    }
                    Flowable.fromArray(searchResults)
                }
    }
}