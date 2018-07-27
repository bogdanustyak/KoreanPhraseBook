package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.PhrasesRequest
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.models.Phrase
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class PhraseRepository(val context: Context) {

    fun getPhrases(categoryName: String): Flowable<List<EPhrase>> {
        Log.d(DialogsRepository.TAG, "getDictionary")
        return getDataFromDB(categoryName)
                .flatMap { phrases ->
                    if (phrases.isNotEmpty()) {
                        return@flatMap Flowable.just(phrases)
                    } else {
                        return@flatMap requestFromNetwork(categoryName)
                                .toFlowable(BackpressureStrategy.LATEST)
                    }
                }
    }

    fun markFavourite(phrase: EPhrase): Completable {
        return Completable.create { emitter ->
            localDB().subscribe({
                try {
                    it.phraseDao().updateFavorite(phrase)
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }, {
                emitter.onError(it)
            })
        }
    }

    private fun getDataFromDB(categoryName: String): Flowable<List<EPhrase>> {
        return AppDataBase.getInstance(context).phraseDao().getByCategory(categoryName)
    }

    private fun requestFromNetwork(categoryName: String): Observable<List<EPhrase>> {
        return PhrasesRequest().getPhrases(categoryName)
                .flatMap {
                    saveIntoDB(it, categoryName)
                }
    }

    private fun saveIntoDB(list: List<Phrase>, categoryName: String): Observable<List<EPhrase>> {
        return Observable.create { emitter ->
            val ePhrases = mapToRoomEntity(list, categoryName)
            emitter.onNext(ePhrases)
            localDB().subscribe { db ->
                db.phraseDao().insertAll(*ePhrases.toTypedArray())
            }
        }
    }

    private fun mapToRoomEntity(list: List<Phrase>, categoryName: String): List<EPhrase> {
        val ePhrases = ArrayList<EPhrase>()
        list.forEach {
            ePhrases.add(EPhrase(it.word, it.translation, it.transcription, it.isFavourite, categoryName))
        }
        return ePhrases
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}