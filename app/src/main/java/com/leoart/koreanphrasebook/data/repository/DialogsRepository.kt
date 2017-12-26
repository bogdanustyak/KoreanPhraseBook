package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * DialogsRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DialogsRepository(private val context: Context) {

    fun getDialogs(): Flowable<List<DialogResponse>> {
        return AppDataBase.getInstance(context).dialogDao().getAll()
                .flatMap { dialogs ->
                    if (dialogs.isNotEmpty()) {
                        return@flatMap Flowable.just(
                                mapDialogs(dialogs)
                        )
                    } else {
                        return@flatMap DialogsRequest().getAllDialogNames()
                                .toFlowable(BackpressureStrategy.LATEST)
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