package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.ui.chapters.models.Chapter
import rx.Observable
import java.util.*

/**
 * Created by bogdan on 11/5/16.
 */
class ChaptersRequest : FireBaseRequest() {
    fun getAllChapters(): Observable<List<Chapter>> {
        return Observable.create({ subscriber ->
            mDataBase.reference?.child("chapters")?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val staffList = ArrayList<Chapter>()
                        for (item in dataSnapshot.children) {
                            var chapter =  item.getValue(Chapter::class.java)
                            chapter.key = item.key
                            staffList.add(chapter)
                        }
                        subscriber.onNext(staffList)
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