package com.leoart.koreanphrasebook.data.parsers.vocabulary

import com.leoart.koreanphrasebook.ui.models.Word
import com.leoart.koreanphrasebook.utils.comparators.LetterComparator
import com.leoart.koreanphrasebook.utils.comparators.WordComparator

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Dictionary(private var data: Map<Char, List<Word>>) {

    constructor() : this(HashMap())

    fun add(letter: Char, words: List<Word>) {
        (this.data as HashMap)[letter] = words
    }

    fun data(): Map<Char, List<Word>> {
        return data.toList().sortedWith(LetterComparator()).toMap()
    }

    fun sortedData(): Map<Char, List<Word>> {
        data.forEach {
            (this.data as HashMap)[it.key] = it.value.sortedWith(WordComparator())
        }
        data = data.toList().sortedWith(LetterComparator()).toMap()
        return data
    }

    fun wordsCount(): Int {
        var count = 0
        for ((_, words) in data) {
            count += words.size
        }
        return count
    }

    fun totalCount(): Int {
        var count = wordsCount()
        count += data.keys.size
        return count
    }
}
