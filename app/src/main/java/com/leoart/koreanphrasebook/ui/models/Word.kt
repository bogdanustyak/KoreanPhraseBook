package com.leoart.koreanphrasebook.ui.models

import com.google.firebase.database.Exclude
import java.util.*

class Word(val word: String, val translation: String, @Exclude var isFavourite: Boolean = false,
           @Exclude val index: Int = 0) {

    @Exclude
    var key = ""

    constructor() : this("", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Word
        return word == that!!.word
    }

    override fun hashCode(): Int {
        return Objects.hash(word, translation)
    }

    @Exclude
    fun toMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put("word", word)
        map.put("translation", translation)
        // map.put("index", index)
        return map
    }
}