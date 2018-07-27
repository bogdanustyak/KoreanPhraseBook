package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.ui.models.Chapter
import io.reactivex.Observable


/**
 * Created by bogdan on 11/5/16.
 */
class ChaptersRequest : FireBaseRequest() {
    fun getAllChapters(): Observable<List<Chapter>> {
        return Observable.create({ subscriber ->
            dataBase.reference.child("chapters").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val staffList = ArrayList<Chapter>()
                    for (item in dataSnapshot.children) {
                        item.key?.let {
                            val chapter = item.getValue(Chapter::class.java) as Chapter
                            chapter.key = it
                            staffList.add(chapter)
                        }
                    }
                    subscriber.onNext(staffList)
                    subscriber.onComplete()
                }
            })
        })
    }
}