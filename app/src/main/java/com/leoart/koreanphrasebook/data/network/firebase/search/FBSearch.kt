package com.leoart.koreanphrasebook.data.network.firebase.search

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.network.firebase.FireBaseRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.ui.models.Phrase
import io.reactivex.Observable
import kotlin.collections.component1
import kotlin.collections.component2

/**
 * Created by bogdan on 6/18/17.
 */
class FBSearch(val searchPhrase: String) : FireBaseRequest() {

    fun search(): Observable<List<SearchResult>> {
        return Observable.concat(searchPhrases(), searchDictionary())
    }

    private fun searchPhrases(): Observable<List<SearchResult>> {
        return Observable.create({ subscriber ->
            mDataBaseRef
                    .child(DictType.CATEGORY_PHRASES.title)
                    //.orderByChild("word").startAt(searchPhrase).endAt("\uf8ff")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot?) {
                            if (dataSnapshot != null) {
                                val list = ArrayList<SearchResult>()
                                dataSnapshot.children.forEach {
                                    it.children.forEach {
                                        val phrase = it.getValue(Phrase::class.java) as Phrase
                                        phrase.key = it.key
                                        val title = when {
                                            phrase.word.contains(searchPhrase, true) -> phrase.word
                                            phrase.translation.contains(searchPhrase, true) -> phrase.translation
                                            phrase.transcription.contains(searchPhrase, true) -> phrase.transcription
                                            else -> ""
                                        }

                                        if (title.isNotEmpty()) {
                                            list.add(SearchResult("", title, DictType.CATEGORY_PHRASES))
                                        }
                                    }
                                }
                                subscriber.onNext(list)
                                subscriber.onComplete()
                            } else {
                                subscriber.onError(Throwable("Data is empty"))
                            }
                        }

                    })
        })
    }

    private fun searchDictionary(): Observable<List<SearchResult>> {
        return Observable.create({ subscriber ->
            mDataBaseRef
                    .child(DictType.DICTIONARY.title)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot?) {
                            if (dataSnapshot != null) {
                                val list = ArrayList<SearchResult>()
                                val dict = dataSnapshot.value as HashMap<*, *>
                                val result = Dictionary()
                                for ((key, value) in dict) {
                                    val words = value as java.util.ArrayList<HashMap<String, String>>
                                    words.forEach {
                                        for (entry in it) {
                                            if (entry.value.contains(searchPhrase, true)) {
                                                list.add(SearchResult(
                                                        "",
                                                        entry.value,
                                                        DictType.DICTIONARY)
                                                )
                                            }
                                        }
                                    }
                                }
                                subscriber.onNext(list)
                                subscriber.onComplete()
                            } else {
                                subscriber.onError(Throwable("Data is empty"))
                            }
                        }

                    })
        })
    }
}