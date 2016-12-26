package com.leoart.koreanphrasebook.data.parsers.vocabulary

import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Dictionary(private var data: HashMap<Char, List<Word>>) {

    constructor() : this(HashMap())

    fun add(letter: Char, words: List<Word>) {
        this.data.put(letter, words)
    }

    fun data(): HashMap<Char, List<Word>> {
        return data
    }

    fun wordsCount(): Int {
        var count = 0
        for ((letter, words) in data) {
            count += words.size
        }
//        data.forEach { letter, words ->
//            if (words != null) {
//                count += words.size
//            }
//        }
        return count
    }

    fun totalCount(): Int {
        return wordsCount() + data.keys.size
    }
}
