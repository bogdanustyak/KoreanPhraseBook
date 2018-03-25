package com.leoart.koreanphrasebook.data.repository.search

import com.leoart.koreanphrasebook.data.network.firebase.search.SearchResult
import io.reactivex.Flowable

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface Search {
    fun search(query: String): Flowable<List<SearchResult>>
}