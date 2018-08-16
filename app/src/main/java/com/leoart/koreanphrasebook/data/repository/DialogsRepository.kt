package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteModel
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteType
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.utils.toCompletable
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

/**
 * DialogsRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DialogsRepository(private val context: Context) : CachedRepository<DialogResponse>, RefreshableRepository {

    companion object {
        val TAG = "DialogsRepository"
    }

    override fun getItems(): Flowable<List<DialogResponse>> {
        Log.d(TAG, "getDialogs")
        return getDataFromDB()
                .doOnNext {
                    if (it.isEmpty()) {
                        requestFromNetwork()
                    }
                }
    }

    override fun getDataFromDB(): Flowable<List<DialogResponse>> {
        Log.d(TAG, "getDataFromDB")
        return AppDataBase.getInstance(context).dialogDao().getAll()
                .flatMap {
                    mapDialogs(it)
                }
    }

    override fun requestFromNetwork() {
        Log.d(TAG, "requestFromNetwork")
        DialogsRequest().getAllDialogNames()
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
    }

    override fun saveIntoDB(items: List<DialogResponse>) {
        val eDialogs = items.map {
            EDialog(it.uid, it.name)
        }.toTypedArray()

        isEmpty().observeOn(Schedulers.io())
                .flatMap {
                    val syncResult: Single<Boolean>
                    if (it.isSyncNeeded) {
                        syncResult = localDB().flatMap { db ->
                            db.dialogDao().insertAll(*eDialogs)
                            DataInfoRepository.getInstance().updateSyncInfo(SyncModel(EDialog::class.java.simpleName, false))
                            Observable.just(true)
                        }.single(false)
                        return@flatMap syncResult
                    } else {
                        return@flatMap Single.just(false)
                    }
                }.subscribe({
                    Log.d(PhraseRepository.TAG,"data saved")
                }, {
                    it.printStackTrace()
                })
    }

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context).dialogDao().count().flatMap {
            Single.just(SyncModel(EDialog::class.java.simpleName, it == 0))
        }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).dialogDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return DialogsRequest().getAllDialogNames()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
                .toCompletable()
    }

    fun getAllDialogReplics(dialogID: String): Observable<List<Replic>> {
        return DialogsRequest().getDialogReplics(dialogID)
    }

    private fun mapDialogs(dialogs: List<EDialog>): Flowable<List<DialogResponse>> {
        return Flowable.fromArray(dialogs.map {
            DialogResponse(
                    it.key,
                    it.dialogTitle
            )
        })
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}