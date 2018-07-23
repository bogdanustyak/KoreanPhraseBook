package com.leoart.koreanphrasebook.ui.vocabulary

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.DictionaryRepository
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.ui.models.Word
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

    fun onFavouriteClicked(word: Word) {
        if (word.isFavourite) {
            dictionaryRepository.markFavourite(EDictionary(word.letter.toCharArray()[0], word.word, word.definition, "false"))
        } else {
            dictionaryRepository.markFavourite(EDictionary(word.letter.toCharArray()[0], word.word, word.definition, "true"))
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