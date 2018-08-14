package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import com.leoart.koreanphrasebook.utils.toCompletable

/**
 * DictionaryRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryRepository(val context: Context) : RefreshableRepository {

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

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context)
                .dictionaryDao()
                .count()
                .map {
                    SyncModel(EDictionary::class.java.simpleName, it == 0)
                }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).dictionaryDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return DictionaryRequest().getDictionary()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.data().isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
                .toCompletable()
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
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.data().isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
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
        isEmpty().observeOn(Schedulers.io())
                .subscribe({
                    if (it.isSyncNeeded) {
                        localDB().subscribe { db ->
                            db.dictionaryDao().insertAll(*dictionary.toTypedArray())
                            DataInfoRepository.getInstance().updateSyncInfo(SyncModel(EDictionary::class.java.simpleName, false))
                        }
                    }
                }, {
                    it.printStackTrace()
                })
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}

