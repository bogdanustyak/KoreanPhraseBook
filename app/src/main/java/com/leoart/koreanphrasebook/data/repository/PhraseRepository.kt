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

class PhraseRepository(val context: Context) : RefreshableRepository{

    fun getPhrases(categoryName: String): Flowable<List<EPhrase>> {
        Log.d(DialogsRepository.TAG, "getDictionary")
        return getDataFromDB(categoryName)
                .doOnNext {
                    if(it.isEmpty()){
                        requestFromNetwork()
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

    private fun requestFromNetwork() : Completable {
        return Completable.create { emitter ->
                    PhrasesRequest().getPhrases()
                            .subscribeOn(Schedulers.io())
                            .subscribe {
                                saveIntoDB(it)
                                emitter.onComplete()
                            }
                }
    }

    private fun saveIntoDB(list: List<EPhrase>) {
            localDB().subscribe { db ->
                db.phraseDao().insertAll(*list.toTypedArray())
            }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Flowable<Boolean> {
        return AppDataBase.getInstance(context).phraseDao().count().flatMap {
            Flowable.just(it == 0)
        }
    }

    override fun refreshData(): Completable {
        return requestFromNetwork()
    }
}