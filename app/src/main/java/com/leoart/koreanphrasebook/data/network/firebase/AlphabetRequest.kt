package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.alphabet.LetterResponse
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import com.leoart.koreanphrasebook.ui.models.Letter
import io.reactivex.Observable
import java.util.*

class AlphabetRequest : FireBaseRequest() {

    val ALPHABET = "alphabet"

    fun fetchAlphabet(): Observable<List<ELetter>> {
        return Observable.create { emitter ->
            dataBase.reference.child(ALPHABET).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    emitter.onError(Throwable("data was not found"))
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val snapList = mutableListOf<ELetter>()
                    for (item in snapshot.children) {
                        item.key?.let {
                            val letter = item.getValue(LetterResponse::class.java) as LetterResponse
                            snapList.add(ELetter(it, letter.koreanLetter, letter.translateLetter))
                        }
                    }
                    emitter.onNext(snapList)
                    emitter.onComplete()
                }

            })
        }
    }

    fun saveAlphabet(alphabet: List<Letter>) {
        alphabet.forEach {
            val key = dataBaseRef.child(ALPHABET).push().key
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("$ALPHABET/$key", it)

            dataBaseRef.updateChildren(childUpdates)
        }
    }

}