package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.chapters.models.DialogsModel
import rx.Observable
import java.util.*

/**
 * Created by khrystyna on 11/24/16.
 */
class DialogsRequest : FireBaseRequest() {

    fun getAllDialogs(): Observable<List<DialogsModel>> {
        return Observable.create({ subscriber ->
            mDataBase.reference?.child("dialogs")?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val categoryList = ArrayList<DialogsModel>()
                        for (item in dataSnapshot.children) {
                            categoryList.add(item.getValue(DialogsModel::class.java))
                        }
                        subscriber.onNext(categoryList)
                        subscriber.onCompleted()
                    } else {
                        subscriber.onError(Throwable("data was not found"))
                        subscriber.onCompleted()
                    }
                }

            })
        })
    }
}