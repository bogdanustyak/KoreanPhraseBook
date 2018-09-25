package com.leoart.koreanphrasebook.ui.models

import com.google.firebase.database.Exclude
import java.util.*

class Word(val word: String, val translation: String,   @Exclude var isFavourite: Boolean = false,
           @Exclude val index: Int = 0) {

    @Exclude var key = ""

    constructor() : this("", "")

    @Exclude
    fun toMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put("word", word)
        map.put("translation", translation)
        // map.put("index", index)
        return map
    }
}