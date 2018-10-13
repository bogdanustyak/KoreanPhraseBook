package com.leoart.koreanphrasebook.data.repository.search

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.search.DictType
import com.leoart.koreanphrasebook.data.network.firebase.search.SearchResult
import com.leoart.koreanphrasebook.data.repository.AppDataBase
import io.reactivex.Flowable
import io.reactivex.functions.Function5


/**
 * SearchRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

private const val minSearchQueryLength = 3
private const val minResultLength = 4

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
                    val searchResults = ArrayList<SearchResult>()
                    chapters.forEach {
                        if (validate(searchQuery, it.name)) {
                            searchResults.add(SearchResult("Path", it.name, DictType.CHAPTERS))
                        }
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchDialogs(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().dialogDao().findByName(searchQuery)
                .toFlowable()
                .flatMap { dialogs ->
                    val searchResults = ArrayList<SearchResult>()
                    dialogs.forEach {
                        if (validate(searchQuery, it.dialogTitle)) {
                            searchResults.add(SearchResult("Path", it.dialogTitle, DictType.DIALOGS))
                        }
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
                        if (validate(searchQuery, it.word)) {
                            searchResults.add(SearchResult("Path", it.word + " " + it.definition, DictType.DICTIONARY))
                        }
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchReplic(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().replicsDao().findByReplic(searchQuery)
                .toFlowable()
                .flatMap { replics ->
                    val searchResults = ArrayList<SearchResult>()
                    replics.forEach {
                        if (validate(searchQuery, it.ukrainian)) {
                            searchResults.add(SearchResult("Path", it.ukrainian, DictType.REPLICS))
                        }
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    fun searchPhrase(searchQuery: String): Flowable<List<SearchResult>> {
        return getDB().phraseDao().findBy(searchQuery)
                .toFlowable()
                .flatMap { replics ->
                    val searchResults = ArrayList<SearchResult>()
                    replics.forEach {
                        if (validate(searchQuery, it.word)) {
                            searchResults.add(SearchResult("Path", it.word + " " + it.translation, DictType.PHRASE))
                        }
                    }
                    Flowable.fromArray(searchResults)
                }
    }

    private fun validate(query: String, matcher: String): Boolean {
        return (query.length == minSearchQueryLength && matcher.length < minResultLength)
                || (query.length > minSearchQueryLength)
    }

    fun getDB(): AppDataBase {
        return AppDataBase.getInstance(context)
    }
}