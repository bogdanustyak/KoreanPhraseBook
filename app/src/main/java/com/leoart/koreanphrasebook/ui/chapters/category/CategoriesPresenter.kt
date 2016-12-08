package com.leoart.koreanphrasebook.ui.chapters.category

import com.leoart.koreanphrasebook.ui.chapters.models.Chapter
import com.leoart.koreanphrasebook.data.network.firebase.CategoriesRequest
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by bogdan on 11/6/16.
 */
class CategoriesPresenter(val view: CategoriesView?) {

    fun requestCategories(chapter: Chapter) {
        CategoriesRequest().getAllCategoriesOfChapter(chapter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    categories ->
                    view?.showCategories(categories)
                })
    }

}