package com.leoart.koreanphrasebook.ui.alphabet

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.leoart.koreanphrasebook.data.repository.AlphabetRepository
import com.leoart.koreanphrasebook.ui.models.Letter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AlphabetViewModel(private val repository : AlphabetRepository) : ViewModel() {

    private val letters : MutableLiveData<List<Letter>> by lazy {
        MutableLiveData<List<Letter>>()
    }

    fun lettersData() : MutableLiveData<List<Letter>>{
        return letters
    }

    fun fetchLetters(){
        repository.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val letters = it.map {
                        Letter(it.uid, it.koreanLetter, it.translateLetter)
                    }
                    this.letters.value = letters
                }, {
                    Log.e(ALPHABET_ERROR, it.message)
                })
    }

    companion object {
        const val ALPHABET_ERROR = "alphabet error"
    }

}