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
                .flatMap {  Flowable.just(mapDialogs(it)) }
                .doOnNext {
                    if(it.isEmpty()){
                        requestFromNetwork()
                    }
                }
    }

    private fun getDataFromDB(): Flowable<List<EDialog>> {
        Log.d(TAG, "getDataFromDB")
        return AppDataBase.getInstance(context).dialogDao().getAll()
    }

    private fun requestFromNetwork() {
        Log.d(TAG, "requestFromNetwork")
        DialogsRequest().getAllDialogNames()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    saveDialogsIntoDB(it)
                }
    }

    private fun saveDialogsIntoDB(dialogNames: List<DialogResponse>) {
            val eDialogs = dialogNames.map {
                EDialog(it.uid, it.name)
            }.toTypedArray()
            localDB().subscribe {
                it.dialogDao().insertAll(*eDialogs)
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

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}