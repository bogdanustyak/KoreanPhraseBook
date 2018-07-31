package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.ui.dialogs.models.Dialog
import io.reactivex.Observable

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DialogsRequest : FireBaseRequest() {

    fun getAllDialogNames(): Observable<List<DialogResponse>> {
        return Observable.create({ emmitter ->
            dataBase.reference.child("dialogs").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    emmitter.onError(Throwable("data was not found"))
                    emmitter.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dialogsList = ArrayList<DialogResponse>()
                    for (item in dataSnapshot.children) {
                        item.key?.let {
                            val dialog = item.getValue(Dialog::class.java) as Dialog
                            dialogsList.add(DialogResponse(it, dialog.name))
                        }
                    }
                    emmitter.onNext(dialogsList)
                    emmitter.onComplete()
                }
            })
        })
    }

    fun getAllDialogReplics(dialogUID: String): Observable<List<Replic>> {
        return Observable.create({ subscriber ->
            dataBase.reference?.child("dialogReplics/" + dialogUID)?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val replics = dataSnapshot.children.map {
                        it.getValue(Replic::class.java) as Replic
                    }
                    subscriber.onNext(replics)
                    subscriber.onComplete()
                }
            })
        })
    }
}