package com.leoart.koreanphrasebook.ui.chapters.category

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.repository.PhraseRepository
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoriesViewModel(private val categoriesRepository: CategoriesRepository) : ViewModel() {

    private var categories: MutableLiveData<List<ECategory>>? = null

    fun getCategories(categoryName: String): LiveData<List<ECategory>> {
        if (categories == null) {
            categories = MutableLiveData()
            loadDictionary(categoryName)
        }
        return categories as MutableLiveData<List<ECategory>>
    }

    private fun loadDictionary(categoryName: String) {
        categoriesRepository
                .getCategories(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dict ->
                    categories?.value = dict
                }, { e ->
                    e.printStackTrace()
                })
    }
}