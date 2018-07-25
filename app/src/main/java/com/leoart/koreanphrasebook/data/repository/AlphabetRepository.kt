package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.AlphabetRequest
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AlphabetRepository(private val context: Context) : CachedRepository<ELetter> {

    override fun getItems(): Flowable<List<ELetter>> {
        return getDataFromDB()
                .flatMap { chapters ->
                    if (chapters.isNotEmpty()) {
                        return@flatMap Flowable.fromArray(chapters)
                    } else {
                        return@flatMap requestFromNetwork()
                                .toFlowable(BackpressureStrategy.LATEST)
                    }
                }
    }

    override fun getDataFromDB(): Flowable<List<ELetter>> {
        return AppDataBase.getInstance(context).letterDao().fetchAll()
    }

    override fun requestFromNetwork(): Observable<List<ELetter>> {
        return AlphabetRequest().fetchAlphabet()
                .flatMap {
                    saveIntoDB(it)
                }
    }

    override fun saveIntoDB(items: List<ELetter>): Observable<List<ELetter>> {
        return Observable.create { emitter ->
            localDB().subscribe {
                it.letterDao().insertAll(*items.toTypedArray())
                emitter.onNext(items)
            }
        }
    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}