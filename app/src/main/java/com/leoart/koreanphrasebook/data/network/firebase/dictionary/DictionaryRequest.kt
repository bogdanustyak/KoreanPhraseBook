package com.leoart.koreanphrasebook.data.network.firebase.dictionary

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Word
import rx.Observable
import java.util.*


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
        return rx.Observable.create({ subscriber ->
            mDataBase.reference?.child(DICTIONARY)?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val dict = dataSnapshot.value as HashMap<*, *>
                        val result = Dictionary()
                        for (entry in dict) {
                            val letter = (entry.key as String)[0]
                            val words = entry.value as ArrayList<HashMap<String, String>>
                            result.add(letter, words)
                        }
                        subscriber.onNext(result)
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