package com.leoart.koreanphrasebook.data.network.firebase.dictionary

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
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

    fun getPhrases(): Observable<List<EPhrase>> {
        return Observable.create ({ subscriber ->
            dataBase.reference.child(CATEGORY_PHRASES).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val phrases = ArrayList<EPhrase>()
                    for (item in dataSnapshot.children) {
                        phrases.addAll(parseCategoryPhrases(item))
                    }
                    subscriber.onNext(phrases)
                    subscriber.onComplete()
                }
            })
        })
    }

    private fun parseCategoryPhrases(dataSnapshot: DataSnapshot) : List<EPhrase>{
        val phrases = mutableListOf<EPhrase>()
        dataSnapshot.key?.let { category ->
            dataSnapshot.children.forEach {
                val phrase = it.getValue(Phrase::class.java) as Phrase
                phrases.add(EPhrase(UUID.randomUUID().toString(),
                        phrase.word, phrase.translation,
                        phrase.transcription, phrase.isFavourite,
                        category))
            }
        }
        return phrases
    }
}