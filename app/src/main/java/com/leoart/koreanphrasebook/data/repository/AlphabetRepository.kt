package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.AlphabetRequest
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import com.leoart.koreanphrasebook.ui.sync.SyncModel
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
        isEmpty().observeOn(Schedulers.io())
                .flatMap {
                    val syncResult: Single<Boolean>
                    if (it.isSyncNeeded) {
                        syncResult = localDB().flatMap { db ->
                            db.letterDao().insertAll(*items.toTypedArray())
                            DataInfoRepository.getInstance().updateSyncInfo(SyncModel(ELetter::class.java.simpleName, false))
                            Observable.just(true)
                        }.single(false)
                        return@flatMap syncResult
                    } else {
                        return@flatMap Single.just(false)
                    }
                }.subscribe({
                    Log.d(TAG,"data saved")
                }, {
                    it.printStackTrace()
                })
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).letterDao().deleteAll()
    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context).letterDao().count().map {
            SyncModel(ELetter::class.java.simpleName, it == 0)
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

    companion object {
        val TAG = "AlphabetRepository"
    }
}