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
            mDataBase.reference?.child("dialogs")?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val dialogsList = ArrayList<DialogResponse>()
                        for (item in dataSnapshot.children) {
                            val uid = item.key
                            val dialog = item.getValue(Dialog::class.java) as Dialog
                            dialogsList.add(DialogResponse(uid, dialog.name))
                        }
                        emmitter.onNext(dialogsList)
                        emmitter.onComplete()
                    } else {
                        emmitter.onError(Throwable("data was not found"))
                        emmitter.onComplete()
                    }
                }
            })
        })
    }

    fun getAllDialogReplics(dialogUID: String): Observable<List<Replic>> {
        return Observable.create({ subscriber ->
            mDataBase.reference?.child("dialogReplics/" + dialogUID)?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val replics = dataSnapshot.children.map {
                            it.getValue(Replic::class.java) as Replic
                        }
                        subscriber.onNext(replics)
                        subscriber.onComplete()
                    } else {
                        subscriber.onError(Throwable("data was not found"))
                        subscriber.onComplete()
                    }
                }
            })
        })
    }
}