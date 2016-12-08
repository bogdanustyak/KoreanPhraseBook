package com.leoart.koreanphrasebook.data.parsers.vocabulary

import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Dictionary @JvmOverloads constructor(private val data: HashMap<Char, List<Word>>) {

    fun add(letter: Char, words: List<Word>) {
        this.data.put(letter, words)
    }

    fun data(): HashMap<Char, List<Word>> {
        return data
    }

    fun wordsCount(): Int {
        var count = 0
        data.forEach { letter, words ->
            count += words.size
        }
        return count
    }
}
