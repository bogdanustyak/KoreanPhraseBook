package com.leoart.koreanphrasebook.data.network.firebase.dictionary

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import io.reactivex.Observable

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryRequest : FireBaseRequest() {

    val DICTIONARY = "dictionary"

    fun initDict(dict: Dictionary) {
        for (entry in dict.data()) {
            val letter = entry.key
            val words = entry.value
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("/$DICTIONARY/$letter", words)
            mDataBaseRef.updateChildren(childUpdates)
        }
    }

    fun getDictionary(): Observable<Dictionary> {
        return Observable.create({ subscriber ->
            mDataBase.reference
                    .child(DICTIONARY)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            subscriber.onError(Throwable("data was not found"))
                            subscriber.onComplete()
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val dict = dataSnapshot.value as HashMap<*, *>
                            val result = Dictionary()
                            for ((key, value) in dict) {
                                val letter = (key as String)[0]
                                val words = value as ArrayList<HashMap<String, String>>
                                result.add(letter, words)
                            }
                            subscriber.onNext(result)
                            subscriber.onComplete()
                        }
                    })
        })
    }
}