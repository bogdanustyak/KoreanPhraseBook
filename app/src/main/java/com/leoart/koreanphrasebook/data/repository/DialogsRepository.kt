package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
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
                    if(it.isEmpty()){
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
                .subscribeOn(Schedulers.io())
                .subscribe {
                    saveIntoDB(it)
                }
    }

    override fun saveIntoDB(items: List<DialogResponse>) {
        val eDialogs = items.map {
            EDialog(it.uid, it.name)
        }.toTypedArray()
        localDB().subscribe {
            it.dialogDao().insertAll(*eDialogs)
        }
    }

    override fun isEmpty(): Flowable<Boolean> {
        return AppDataBase.getInstance(context).dialogDao().count().flatMap {
            Flowable.just(it == 0)
        }
    }

    override fun refreshData(): Completable {
        return Completable.create { emitter ->
            DialogsRequest().getAllDialogNames()
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        saveIntoDB(it)
                        emitter.onComplete()
                    }
        }
    }

    fun getAllDialogReplics(dialogID: String): Observable<List<Replic>> {
        return DialogsRequest().getAllDialogReplics(dialogID)
    }

    private fun mapDialogs(dialogs: List<EDialog>): Flowable<List<DialogResponse>> {
        return Flowable.fromArray(dialogs.map {
            DialogResponse(
                    it.uid,
                    it.dialogTitle
            )
        })
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}