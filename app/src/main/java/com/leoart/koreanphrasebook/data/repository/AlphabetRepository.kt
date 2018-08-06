package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.AlphabetRequest
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AlphabetRepository(private val context: Context) : CachedRepository<ELetter>, RefreshableRepository {


    override fun getItems(): Flowable<List<ELetter>> {
        return getDataFromDB()
                .doOnNext{
                    if(it.isEmpty()) {
                        requestFromNetwork()
                    }
                }
    }

    override fun getDataFromDB(): Flowable<List<ELetter>> {
        return AppDataBase.getInstance(context).letterDao().fetchAll()
    }

    override fun requestFromNetwork() {
        AlphabetRequest().fetchAlphabet()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    saveIntoDB(it)
                }
    }

    override fun saveIntoDB(items: List<ELetter>) {
        localDB().subscribe {
            it.letterDao().insertAll(*items.toTypedArray())
        }
    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Flowable<Boolean> {
        return AppDataBase.getInstance(context).letterDao().count().flatMap {
            Flowable.just(it == 0)
        }
    }

    override fun refreshData(): Completable {
        return Completable.create { emitter ->
            AlphabetRequest().fetchAlphabet()
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        saveIntoDB(it)
                        emitter.onComplete()
                    }
        }
    }
}