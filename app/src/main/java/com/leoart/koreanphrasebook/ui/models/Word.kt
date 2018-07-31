package com.leoart.koreanphrasebook.ui.models

import com.google.firebase.database.Exclude
import java.util.HashMap

class Word(val letter: String, val word: String, val definition: String, val index: Int,
           var isFavourite: Boolean = false) {

    var key = ""

    constructor() : this("", "", "", 0)

    @Exclude
    fun toMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put("letter", letter.toString())
        map.put("word", word)
        map.put("definition", definition)
        // map.put("index", index)
        return map
    }
}