package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.AlphabetRequest
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import com.leoart.koreanphrasebook.utils.toCompletable
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class AlphabetRepository(private val context: Context) : CachedRepository<ELetter>, RefreshableRepository {

    override fun getItems(): Flowable<List<ELetter>> {
        return getDataFromDB()
                .doOnNext {
                    if (it.isEmpty()) {
                        requestFromNetwork()
                    }
                }
    }

    override fun getDataFromDB(): Flowable<List<ELetter>> {
        return AppDataBase.getInstance(context).letterDao().fetchAll()
    }

    override fun requestFromNetwork() {
        AlphabetRequest().fetchAlphabet()
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
    }

    override fun saveIntoDB(items: List<ELetter>) {
        localDB().subscribe {
            it.letterDao().insertAll(*items.toTypedArray())
        }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).letterDao().deleteAll()
    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Single<Boolean> {
        return AppDataBase.getInstance(context).letterDao().count().flatMap {
            Single.just(it == 0)
        }
    }

    override fun refreshData(): Completable {
        return AlphabetRequest().fetchAlphabet()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
                .toCompletable()
    }

}