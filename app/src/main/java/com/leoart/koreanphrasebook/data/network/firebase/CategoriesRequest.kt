package com.leoart.koreanphrasebook.data.network.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.ui.models.Category
import com.leoart.koreanphrasebook.ui.models.Chapter
import io.reactivex.Observable

/**
 * Created by bogdan on 11/5/16.
 */
class CategoriesRequest : FireBaseRequest() {

    val CATEGORIES = "chapterCategories"

    fun writeCategories(chapter: String, phrases: List<Category>) {
        var count = 34
        phrases.forEach {
            val key = "category" + count
            val childUpdates = HashMap<String, Any>()
            childUpdates.put("$CATEGORIES/$chapter/$key", it.name)
            dataBaseRef.updateChildren(childUpdates)
            count++
        }
    }

    fun getAllCategoriesOfChapter(chapter: Chapter): Observable<List<Category>> {
        val chapterName = chapter.key
        return Observable.create({ subscriber ->
            dataBase.reference?.child("$CATEGORIES/$chapterName")?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val categoryList = ArrayList<Category>()
                    for (item in dataSnapshot.children) {
                        item.key?.let {
                            Log.d("test", item.value.toString())
                            val categoryPhrase = item
                                    .value
                                    as HashMap<String, String>
                            Log.d("tes", categoryPhrase.toString())
                            val category = Category(it, categoryPhrase)
                            categoryList.add(category)
                        }
                    }
                    subscriber.onNext(categoryList)
                    subscriber.onComplete()
                }
            })
        })
    }

    fun getAllCategories(): Observable<List<Category>> {
        return Observable.create({ subscriber ->
            dataBase.reference.child("categories").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val categoryList = ArrayList<Category>()
                    for (item in dataSnapshot.children) {
                        val category = item.value as Category
                        //category.id = item.key
                        categoryList.add(category)
                    }
                    subscriber.onNext(categoryList)
                    subscriber.onComplete()
                }
            })
        })
    }
}