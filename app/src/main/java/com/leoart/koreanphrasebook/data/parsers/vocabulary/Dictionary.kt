package com.leoart.koreanphrasebook.data.parsers.vocabulary

import com.leoart.koreanphrasebook.utils.Alphabet
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Dictionary(private var data: HashMap<Char, List<HashMap<String, String>>>) {

    constructor() : this(HashMap())

    fun add(letter: Char, words: List<HashMap<String, String>>) {
        this.data.put(letter, words)
    }

    fun data(): HashMap<Char, List<HashMap<String, String>>> {
        return data
    }

    fun sortedData(): Map<Char, List<HashMap<String, String>>> {
        return data.toSortedMap(compareBy<Char> {
            Alphabet().getUkrAlphabet().indexOf(it)
        })
    }

    fun wordsCount(): Int {
        var count = 0
        for ((letter, words) in data) {
            count += words.size
        }
        return count
    }

    fun totalCount(): Int {
        var count = wordsCount()
        count += data.keys.size
        return count
    }

    fun positionOf(letter: Char): Int {
        var position = 0
        val sorted = sortedData()
        for ((key, value) in sorted) {
            if (letter == key) {
                break
            } else {
                position += value.size
            }
        }
        return position
    }
}
