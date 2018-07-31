package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * DictionaryRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryRepository(val context: Context) {

    fun getDictionary(): Flowable<Dictionary> {
        Log.d(DialogsRepository.TAG, "getDialogs")
        return getDataFromDB()
                .doOnNext {
                    if(it.data().isEmpty()){
                        requestFromNetwork()
                    }
                }
    }

    fun getDataFromDB(): Flowable<Dictionary> {
        return AppDataBase.getInstance(context).dictionaryDao().getAll()
                .flatMap {
                    mapDict(it)
                }
    }

    private fun mapDict(dict: List<EDictionary>): Flowable<Dictionary> {
        if (dict.isEmpty()) return Flowable.just(Dictionary())
        val dictionary = Dictionary()
        var previouseLetter: Char = dict.first().letter
        var list: ArrayList<HashMap<String, String>> = ArrayList<HashMap<String, String>>()
        var map = HashMap<String, String>()

        dict.forEach { d ->
            if (previouseLetter == d.letter) {
                map.put("translation", d.definition)
                map.put("word", d.word)
                list.add(map)
                map = HashMap<String, String>()
            } else {
                dictionary.add(previouseLetter, list)
                previouseLetter = d.letter
                list = ArrayList<HashMap<String, String>>()
                map = HashMap<String, String>()
            }
        }

        return Flowable.just(dictionary)
    }

    fun requestFromNetwork() {
        DictionaryRequest().getDictionary()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    saveIntoDB(it)
                }
    }

    fun saveIntoDB(dict: Dictionary) {
        val dictionary: ArrayList<EDictionary> = ArrayList()
        dict.data().forEach {
            val key = it.key
            val value = it.value
            value.forEach {
                dictionary.add(EDictionary(key, it["word"] ?: "", it["translation"] ?: ""))
            }
        }

        localDB().subscribe { db ->
            db.dictionaryDao().insertAll(*dictionary.toTypedArray())
        }
    }


    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}
