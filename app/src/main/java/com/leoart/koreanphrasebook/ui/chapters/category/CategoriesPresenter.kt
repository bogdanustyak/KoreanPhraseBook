package com.leoart.koreanphrasebook.ui.chapters.category

import com.leoart.koreanphrasebook.data.network.firebase.CategoriesRequest
import com.leoart.koreanphrasebook.ui.models.Chapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by bogdan on 11/6/16.
 */
class CategoriesPresenter(val view: CategoriesView?) {

    fun requestCategories(chapter: Chapter) {
        CategoriesRequest().getAllCategoriesOfChapter(chapter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categories ->
                    view?.showCategories(categories)
                })
    }

}