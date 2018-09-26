package com.leoart.koreanphrasebook.utils

import com.leoart.koreanphrasebook.ui.models.Word
import java.text.Collator
import java.util.*

/**
 * Created by Maryan Onysko (maryan.onysko@gmail.com)
 */

class LetterComparator() : Comparator<Pair<Char, List<Word>>> {
    override fun compare(p0: Pair<Char, List<Word>>?, p1: Pair<Char, List<Word>>?): Int {
        val usCollator = Collator.getInstance(Locale.UK)
        usCollator.strength = Collator.PRIMARY
        return usCollator.compare(p0?.first.toString(), p1?.first.toString())
    }
}