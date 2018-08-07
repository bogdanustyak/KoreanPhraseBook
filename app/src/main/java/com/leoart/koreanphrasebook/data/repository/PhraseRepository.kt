package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.PhrasesRequest
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.models.Phrase
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class PhraseRepository(val context: Context) : RefreshableRepository {

    fun getPhrases(categoryName: String): Flowable<List<EPhrase>> {
        Log.d(DialogsRepository.TAG, "getDictionary")
        return getDataFromDB(categoryName)
                .doOnNext {
                    if (it.isEmpty()) {
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

    private fun requestFromNetwork() {
        PhrasesRequest().getPhrases()
                .observeOn(Schedulers.io())
                .subscribe {
                    clearDB()
                    saveIntoDB(it)
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

    override fun isEmpty(): Single<Boolean> {
        return AppDataBase.getInstance(context)
                .phraseDao()
                .count()
                .map {
                    it == 0
                }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).phraseDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return Completable.create { emitter ->
            PhrasesRequest().getPhrases()
                    .observeOn(Schedulers.io())
                    .subscribe {
                        clearDB()
                        saveIntoDB(it)
                        emitter.onComplete()
                    }
        }
    }
}