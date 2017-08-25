package com.leoart.koreanphrasebook.data.network.firebase.dictionary

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.CategoriesRequest
import com.leoart.koreanphrasebook.data.network.firebase.ChaptersRequest
import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.ui.models.Phrase
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*


/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class PhrasesRequest : FireBaseRequest() {

    val CATEGORY_PHRASES = "categoryPhrases"

    fun writePhrases(category: String, phrases: List<Phrase>) {
        phrases.forEach {
            val key = mDataBaseRef.child("$CATEGORY_PHRASES/$category").push().key
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("$CATEGORY_PHRASES/$category/$key", it.toMap())

            mDataBaseRef.updateChildren(childUpdates)
        }
    }


    fun getPhrases(categoryName: String): Observable<List<Phrase>> {
        return Observable.create({ subscriber ->
            mDataBase.reference?.child("$CATEGORY_PHRASES/$categoryName")?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val phrases = ArrayList<Phrase>()
                        for (item in dataSnapshot.children) {
                            val phrase = item.getValue(Phrase::class.java) as Phrase
                            phrase.key = item.key
                            phrases.add(phrase)
                        }
                        subscriber.onNext(phrases)
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