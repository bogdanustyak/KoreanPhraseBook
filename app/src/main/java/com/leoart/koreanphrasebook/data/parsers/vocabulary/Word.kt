package com.leoart.koreanphrasebook.data.parsers.vocabulary

import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Word(val word: String, val translation: String) {

    fun toMap(): HashMap<String, Any> {
        val map = HashMap<String, Any>();
        map.put("word", word)
        map.put("translation", translation)
        return map
    }
}