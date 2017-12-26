package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import io.reactivex.Observable

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryRepository(val context: Context) {

    fun getDictionary(): Observable<Dictionary> {
        //todo
        return DictionaryRequest()
                .getDictionary()
        //val cashedData = AppDataBase.getInstance(context).dictionaryDao().getAll()
//        return if (cashedData.isNotEmpty()) {
//            Observable.just(Dictionary(cashedData.first().data))
//        } else {
//            DictionaryRequest()
//                    .getDictionary()
//        }
    }
}