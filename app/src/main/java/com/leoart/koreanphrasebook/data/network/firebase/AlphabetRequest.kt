package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.alphabet.LetterResponse
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import io.reactivex.Observable

class AlphabetRequest : FireBaseRequest() {

    fun fetchAlphabet() : Observable<List<ELetter>> {
        return Observable.create { emitter ->
            mDataBase.reference.child("alphabet").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    emitter.onError(Throwable("data was not found"))
                    emitter.onComplete()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val snapList = mutableListOf<ELetter>()
                    for (item in snapshot.children) {
                        item.key?.let {
                            val letter = item.getValue(LetterResponse::class.java) as LetterResponse
                            snapList.add(ELetter(it, letter.korean, letter.ukrainian))
                        }
                    }
                    emitter.onNext(snapList)
                    emitter.onComplete()
                }

            })
        }
    }

}