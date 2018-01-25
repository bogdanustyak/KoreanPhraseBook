package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * DialogsRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DialogsRepository(private val context: Context) {

    companion object {
        val TAG = "DialogsRepository"
    }

    fun getDialogs(): Flowable<List<DialogResponse>> {
        Log.d(TAG, "getDialogs")
        return getDataFromDB()
                .flatMap { dialogs ->
                    if (dialogs.isNotEmpty()) {
                        return@flatMap Flowable.just(
                                mapDialogs(dialogs)
                        )
                    } else {
                        return@flatMap requestFromNetwork()
                                .toFlowable(BackpressureStrategy.LATEST)
                    }
                }
    }

    private fun getDataFromDB(): Flowable<List<EDialog>> {
        Log.d(TAG, "getDataFromDB")
        return AppDataBase.getInstance(context).dialogDao().getAll()
    }

    private fun requestFromNetwork(): Observable<List<DialogResponse>> {
        Log.d(TAG, "requestFromNetwork")
        return DialogsRequest().getAllDialogNames()
                .flatMap {
                    saveDialogsIntoDB(it)
                }
    }

    private fun saveDialogsIntoDB(dialogNames: List<DialogResponse>): Observable<List<DialogResponse>> {
        return Observable.create { emitter ->
            val eDialogs = dialogNames.map {
                EDialog(it.uid, it.name)
            }.toTypedArray()
            Observable.just(AppDataBase.getInstance(context))
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        it.dialogDao().insertAll(*eDialogs)
                        emitter.onNext(dialogNames)
                    }
        }
    }

    fun getAllDialogReplics(dialogID: String): Observable<List<Replic>> {
        return DialogsRequest().getAllDialogReplics(dialogID)
    }

    private fun mapDialogs(dialogs: List<EDialog>): List<DialogResponse> {
        return dialogs.map {
            DialogResponse(
                    it.uid,
                    it.dialogTitle
            )
        }
    }
}