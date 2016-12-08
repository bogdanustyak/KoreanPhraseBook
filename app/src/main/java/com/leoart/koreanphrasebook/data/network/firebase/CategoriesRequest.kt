package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.leoart.koreanphrasebook.ui.chapters.models.Category
import com.leoart.koreanphrasebook.ui.chapters.models.Chapter
import rx.Observable
import java.util.*

/**
 * Created by bogdan on 11/5/16.
 */
class CategoriesRequest : FireBaseRequest() {

    fun getAllCategoriesOfChapter(chapter: Chapter): Observable<List<Category>> {
        return getAllCategories()
//        return Observable.create({ subscriber ->
//
//
//            val categoriesRef = mDataBase.reference.child("categories")
//            val categories = mutableListOf<Category>()
//
//            for (category in chapter.categories.keys) {
//                val categoryRef = categoriesRef?.child(category)
//
//                categoryRef
//                        ?.addListenerForSingleValueEvent(object : ValueEventListener {
//                            override fun onCancelled(p0: DatabaseError?) {
//                                throw UnsupportedOperationException("not implemented")
//                            }
//
//                            override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                                if (dataSnapshot != null) {
//                                    val cat = dataSnapshot.getValue(Category::class.java)
//                                    categories.add(cat)
//                                    subscriber.onNext(categories)
//                                    subscriber.onCompleted()
//                                }
//                            }
//
//                        })
//            }
//        })
    }

    fun getAllCategories(): Observable<List<Category>> {
        return Observable.create({ subscriber ->
            mDataBase.reference?.child("categories")?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    throw UnsupportedOperationException("not implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot != null) {
                        val categoryList = ArrayList<Category>()
                        for (item in dataSnapshot.children) {
                            categoryList.add(item.getValue(Category::class.java))
                        }
                        subscriber.onNext(categoryList)
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