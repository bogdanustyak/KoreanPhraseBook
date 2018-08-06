package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

/**
 * DictionaryRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryRepository(val context: Context) : RefreshableRepository{

    fun getDictionary(): Flowable<Dictionary> {
        Log.d(DialogsRepository.TAG, "getDictionary")
        return getDataFromDB()
                .doOnNext {
                    if (it.data().isEmpty()) {
                        requestFromNetwork()
                    }
                }
    }

    fun markFavourite(dict: EDictionary): Completable {
        return Completable.create { emitter ->
            localDB().subscribe({
                try {
                    it.dictionaryDao().updateFavorite(dict)
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }, {
                emitter.onError(it)
            })
        }
    }


    fun getDataFromDB(): Flowable<Dictionary> {
        return AppDataBase.getInstance(context).dictionaryDao().getAll()
                .flatMap {
                    mapDict(it)
                }
    }

    override fun isEmpty(): Single<Boolean> {
        return AppDataBase.getInstance(context).dictionaryDao().count().flatMap {
            Single.just(it == 0)
        }
    }

    override fun refreshData(): Completable {
        return Completable.create { emitter ->
            DictionaryRequest().getDictionary()
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        saveIntoDB(it)
                        emitter.onComplete()
                    }
        }
    }

    private fun mapDict(dict: List<EDictionary>): Flowable<Dictionary> {
        if (dict.isEmpty()) return Flowable.just(Dictionary())
        val dictionary = Dictionary()
        var previouseLetter: Char = dict.first().letter
        var list: ArrayList<HashMap<String, String>> = ArrayList<HashMap<String, String>>()

        dict.forEach { d ->
            if (previouseLetter == d.letter) {
                list.add(generateDataMap(d.definition, d.word, d.isFavourite))
            } else {
                dictionary.add(previouseLetter, list)
                previouseLetter = d.letter
                list = ArrayList<HashMap<String, String>>()
                list.add(generateDataMap(d.definition, d.word, d.isFavourite))
            }
        }
        if (list.size > 0) {
            dictionary.add(previouseLetter, list)
        }
        return Flowable.just(dictionary)
    }

    private fun generateDataMap(translation: String, word: String, favourite: String): HashMap<String, String> {
        var map = HashMap<String, String>()
        map.put("translation", translation)
        map.put("word", word)
        map.put("favourite", favourite)
        return map
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
                dictionary.add(EDictionary(key, it["word"] ?: "", it["translation"]
                        ?: "", it["favourite"] ?: "false"))
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
