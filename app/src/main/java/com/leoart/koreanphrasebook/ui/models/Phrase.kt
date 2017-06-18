package com.leoart.koreanphrasebook.ui.models

import com.google.firebase.database.Exclude
import java.util.*

/**
 * Created by bogdan on 11/5/16.
 */
class Phrase(val word: String, val translation: String, val transcription: String, val index: Int) {

    constructor() : this("", "", "", 0)

    @Exclude
    fun toMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put("word", word)
        map.put("translation", translation)
        map.put("transcription", transcription)
        // map.put("index", index)
        return map
    }
}
