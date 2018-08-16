package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.data.repository.AppDataBase
import com.leoart.koreanphrasebook.data.repository.DataInfoRepository
import com.leoart.koreanphrasebook.data.repository.DialogsRepository
import com.leoart.koreanphrasebook.data.repository.RefreshableRepository
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.utils.toCompletable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class DiealogRepository(val context: Context) : RefreshableRepository {

    fun getDialog(categoryName: String): Flowable<List<EReplic>> {
        Log.d(DialogsRepository.TAG, "getDictionary")
        return getDataFromDB(categoryName)
                .doOnNext {
                    if (it.isEmpty()) {
                        requestFromNetwork()
                    }
                }
    }

    private fun getDataFromDB(categoryName: String): Flowable<List<EReplic>> {
        return AppDataBase.getInstance(context).replicsDao().getByUid(categoryName)
    }

    private fun requestFromNetwork() {
        DialogsRequest().getAllDialogsReplics()
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(mapToRoomEntity(it))
                    }
                }
    }

    private fun mapToRoomEntity(list: List<Replic>): List<EReplic> {
        val eReplic = ArrayList<EReplic>()
        list.forEach {
            eReplic.add(EReplic(it.uid, it.korean, it.ukrainian, it.number))
        }
        return eReplic
    }

    private fun saveIntoDB(list: List<EReplic>) {
        isEmpty().observeOn(Schedulers.io())
                .flatMap {
                    val syncResult: Single<Boolean>
                    if (it.isSyncNeeded) {
                        syncResult = localDB().flatMap { db ->
                            db.replicsDao().insertAll(*list.toTypedArray())
                            Observable.just(true)
                        }.single(false)
                        return@flatMap syncResult
                    } else {
                        return@flatMap Single.just(false)
                    }
                }.subscribe({
                    if (it == true) {
                        DataInfoRepository.getInstance().updateSyncInfo(SyncModel(EReplic::class.java.simpleName, false))
                    }
                }, {
                    it.printStackTrace()
                })
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context)
                .phraseDao()
                .count()
                .map {
                    SyncModel(EReplic::class.java.simpleName, it == 0)
                }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).phraseDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return DialogsRequest().getAllDialogsReplics()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(mapToRoomEntity(it))
                    }
                }
                .toCompletable()
    }
}