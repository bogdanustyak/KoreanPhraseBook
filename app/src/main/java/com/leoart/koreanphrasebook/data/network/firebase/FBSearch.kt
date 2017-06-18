package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import rx.Observable

/**
 * Created by bogdan on 6/18/17.
 */
class FBSearch(val searchPhrase: String) : FireBaseRequest() {

    fun search(): Observable<List<String>> {
        return Observable.create({ subscriber ->
            mDataBaseRef.orderByKey().startAt(searchPhrase).endAt(searchPhrase + "\uf8ff")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot?) {
                            if (dataSnapshot != null) {
                                val list = ArrayList<String>()
                                for (item in dataSnapshot.children){
                                    list.add(item.value.toString())
                                }
                                subscriber.onNext(list)
                                subscriber.onCompleted()
                            } else {
                                subscriber.onError(Throwable("Data is empty"))
                            }
                        }

                    })
        })
    }
}