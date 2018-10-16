package com.leoart.koreanphrasebook.data.parsers.alphabet

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.AlphabetRequest
import com.leoart.koreanphrasebook.data.parsers.vocabulary.DictionaryStream

class AlphabetParser(private val context: Context) {

    fun writeToFirebaseDB() {
        val alphabet = AlphabetStream(context.assets.open("alphabet.txt")).parse()
        AlphabetRequest().saveAlphabet(alphabet)
    }
}