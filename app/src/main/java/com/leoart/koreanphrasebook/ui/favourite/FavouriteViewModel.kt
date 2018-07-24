package com.leoart.koreanphrasebook.ui.favourite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.parsers.favourite.Favourite
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteType
import com.leoart.koreanphrasebook.data.repository.FavouriteRepository
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavouriteViewModel(private val favouriteRepository: FavouriteRepository) : ViewModel() {

    private var favourite: MutableLiveData<List<Favourite>>? = null

    fun getData(): LiveData<List<Favourite>> {
        if (favourite == null) {
            favourite = MutableLiveData()
            loadDictionary()
        }
        return favourite as MutableLiveData<List<Favourite>>
    }

    fun onFavouriteClicked(favourite: Favourite) {
        favouriteRepository.markFavourite(favourite)
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