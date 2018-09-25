package com.leoart.koreanphrasebook.data.network.firebase.dictionary

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.ui.models.Phrase
import io.reactivex.Observable
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class PhrasesRequest : FireBaseRequest() {

    val CATEGORY_PHRASES = "categoryPhrases"

    fun writePhrases(category: String, phrases: List<Phrase>) {
        phrases.forEach {
            val key = dataBaseRef.child("$CATEGORY_PHRASES/$category").push().key
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("$CATEGORY_PHRASES/$category/$key", it.toMap())

            dataBaseRef.updateChildren(childUpdates)
        }
    }

    fun getPhrases(): Observable<List<Phrase>> {
        return Observable.create { subscriber ->
            dataBase.reference.child(CATEGORY_PHRASES).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val phrases = ArrayList<Phrase>()
                    for (item in dataSnapshot.children) {
                        item.key?.let { category ->
                            item.children.forEach {
                                val phrase = it.getValue(Phrase::class.java) as Phrase
                                phrase.key = category
                                phrases.add(phrase)
                            }
                        }
                    }
                    subscriber.onNext(phrases)
                    subscriber.onComplete()
                }
            })
        }
    }
}