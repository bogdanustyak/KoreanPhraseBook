package com.leoart.koreanphrasebook.ui.chapters.phrase

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.repository.PhraseRepository
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PhraseViewModel(private val phrasesRepository: PhraseRepository) : ViewModel() {

    private var phrases: MutableLiveData<List<EPhrase>>? = null

    fun getPhrases(categoryName: String): LiveData<List<EPhrase>> {
        if (phrases == null) {
            phrases = MutableLiveData()
            loadDictionary(categoryName)
        }
        return phrases as MutableLiveData<List<EPhrase>>
    }

    fun onFavouriteClicked(phrase: EPhrase) {
        phrase.isFavourite = !phrase.isFavourite
        phrasesRepository.markFavourite(phrase)
    }

    private fun loadDictionary(categoryName: String) {
        phrasesRepository
                .getPhrases(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dict ->
                    phrases?.value = dict
                }, { e ->
                    e.printStackTrace()
                })
    }
}