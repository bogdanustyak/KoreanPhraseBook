package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * DictionaryRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryRepository(val context: Context) {

    fun getDictionary(): Flowable<Dictionary> {
        Log.d(DialogsRepository.TAG, "getDialogs")
        return requestFromNetwork()
                .toFlowable(BackpressureStrategy.LATEST)
//        return getDataFromDB()
//                .flatMap { dictionary ->
//                    if (dictionary != null) {
//                        return@flatMap Flowable.just(dictionary)
//                    } else {
//                        return@flatMap requestFromNetwork()
//                                .toFlowable(BackpressureStrategy.LATEST)
//                    }
//                }
    }

//    fun getDataFromDB(): Flowable<Dictionary> {
//        return AppDataBase.getInstance(context).dictionaryDao().getAll()
//                .flatMap {
//                    mapDict(it)
//                }
//    }

//    private fun mapDict(dict: List<EDictionary>): Flowable<Dictionary> {
//        return Flowable.just(dict.map {
//        })
//    }

    fun requestFromNetwork(): Observable<Dictionary> {
        return DictionaryRequest().getDictionary()
//                .flatMap {
//                    saveIntoDB(it)
//                }
    }

    fun saveIntoDB(dict: Dictionary): Observable<Dictionary> {
        return Observable.create { emitter ->
            val eDict = dict.data().map {
                val letter = it.key
                it.value.map {
                    EDictionary(
                            letter,
                            it["word"] ?: "",
                            it["translation"] ?: ""
                    )
                }
            }.toTypedArray()
            localDB().subscribe { db ->
                eDict.forEach {
                    it.forEach {
                        db.dictionaryDao().insertAll(it)
                    }
                }
                emitter.onNext(dict)
            }
        }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}