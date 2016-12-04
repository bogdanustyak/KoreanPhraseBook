package com.leoart.koreanphrasebook.data.parsers.vocabulary

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.DictionaryRequest

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DictionaryParser(private val context: Context) {

    fun writeToFirebaseDB() {
        val dict = DictionaryStream(context.assets.open("vocabulary.txt")).parse()

        DictionaryRequest().initDict(dict)

    }

}