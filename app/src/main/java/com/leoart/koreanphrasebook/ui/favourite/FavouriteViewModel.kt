package com.leoart.koreanphrasebook.ui.favourite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteModel
import com.leoart.koreanphrasebook.data.repository.FavouriteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository) : ViewModel() {

    private var favourite: MutableLiveData<List<FavouriteModel>>? = null

    fun getData(): LiveData<List<FavouriteModel>> {
        if (favourite == null) {
            favourite = MutableLiveData()
            loadDictionary()
        }
        return favourite as MutableLiveData<List<FavouriteModel>>
    }

    fun onFavouriteClicked(favourite: FavouriteModel) {
        favouriteRepository
                .markFavourite(favourite)
                .subscribe({}, {
                    it.printStackTrace()
                })
    }

    private fun loadDictionary() {
        favouriteRepository
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    favourite?.value = it
                }, { e ->
                    e.printStackTrace()
                })
    }
}