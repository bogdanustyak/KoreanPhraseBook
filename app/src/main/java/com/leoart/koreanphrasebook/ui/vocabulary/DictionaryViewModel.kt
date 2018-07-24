package com.leoart.koreanphrasebook.ui.vocabulary

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.DictionaryRepository
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryViewModel(private val dictionaryRepository: DictionaryRepository) : ViewModel() {

    private var dictionary: MutableLiveData<Dictionary>? = null

    fun geDictionary(): LiveData<Dictionary> {
        if (dictionary == null) {
            dictionary = MutableLiveData()
            loadDictionary()
        }
        return dictionary as MutableLiveData<Dictionary>
    }

    fun onFavouriteClicked(dictionary: EDictionary) {
        if (dictionary.isFavourite.toBoolean()) {
            dictionaryRepository.markFavourite(EDictionary(dictionary.letter, dictionary.word, dictionary.definition, "false"))
        } else {
            dictionaryRepository.markFavourite(EDictionary(dictionary.letter, dictionary.word, dictionary.definition, "true"))
        }
    }

    private fun loadDictionary() {
        dictionaryRepository
                .getDictionary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dict ->
                    dictionary?.value = dict
                }, { e ->
                    e.printStackTrace()
                })
    }
}