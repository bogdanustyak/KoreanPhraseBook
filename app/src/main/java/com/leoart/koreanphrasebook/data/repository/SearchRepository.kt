package com.leoart.koreanphrasebook.data.repository

import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import io.reactivex.Observable

/**
 * SearchRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchRepository(val dictionaryRepository: DictionaryRepository) {

    fun searchDict(query: String): Observable<Dictionary> {
        return Observable.empty()
//        return dictionaryRepository
//                .getDictionary()
    }
}