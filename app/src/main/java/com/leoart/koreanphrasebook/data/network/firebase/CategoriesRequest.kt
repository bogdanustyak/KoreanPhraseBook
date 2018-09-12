package com.leoart.koreanphrasebook.data.network.firebase

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
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

    /**
     * deprecated but might be reused soon
     */
//    fun getAllCategoriesOfChapter(chapter: Chapter): Observable<List<Category>> {
//        val chapterName = chapter.key
//        return Observable.create({ subscriber ->
//            dataBase.reference?.child("$CATEGORIES/$chapterName")?.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//                    subscriber.onError(Throwable("data was not found"))
//                    subscriber.onComplete()
//                }
//
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                    val categoryList = ArrayList<Category>()
//                    for (item in dataSnapshot.children) {
//                        item.key?.let {
//                            val categoryPhrase = item
//                                    .value
//                                    as HashMap<String, String>
//                            val category = Category(it, categoryPhrase)
//                            categoryList.add(category)
//                        }
//                    }
//                    subscriber.onNext(categoryList)
//                    subscriber.onComplete()
//                }
//            })
//        })
//    }

    fun getAllCategories(): Observable<List<Category>> {
        return Observable.create { subscriber ->
            dataBase.reference.child(CATEGORIES).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    subscriber.onError(Throwable("data was not found"))
                    subscriber.onComplete()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val categoryList = ArrayList<Category>()
                    for(item in dataSnapshot.children){
                        item.key?.let{
                            val id = it
                            for (inItem in item.children){
                                inItem.key?.let{
                                    categoryList.add(Category(id,inItem.value as HashMap<String, String>, it))
                                }
                            }
                        }
                    }
                    subscriber.onNext(categoryList)
                    subscriber.onComplete()
                }
            })
        }
    }
}