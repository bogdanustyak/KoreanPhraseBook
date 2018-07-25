package com.leoart.koreanphrasebook.ui.alphabet

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.ui.models.Letter

class AlphabetViewModel : ViewModel() {

    private val letters : MutableLiveData<List<Letter>> by lazy {
        MutableLiveData<List<Letter>>()
    }

    fun lettersData() : MutableLiveData<List<Letter>>{
        return letters
    }

    fun fetchLetters(){
        val stubList = mutableListOf<Letter>(
                Letter("1", "a", "s"),
                Letter("2", "o", "t"),
                Letter("3", "j", "y"),
                Letter("4", "g", "u"),
                Letter("5", "z", "i")
        )
        letters.value = stubList
    }

}