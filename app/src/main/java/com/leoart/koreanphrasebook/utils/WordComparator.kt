package com.leoart.koreanphrasebook.utils

import com.leoart.koreanphrasebook.ui.models.Word
import java.text.Collator
import java.util.*

/**
 * Created by Maryan Onysko (maryan.onysko@gmail.com)
 */
class WordComparator : Comparator<Word> {
    override fun compare(p0: Word?, p1: Word?): Int {
        val collator = Collator.getInstance(Locale.UK)
        collator.strength = Collator.PRIMARY
        return collator.compare(p0?.word, p1?.word)
    }
}