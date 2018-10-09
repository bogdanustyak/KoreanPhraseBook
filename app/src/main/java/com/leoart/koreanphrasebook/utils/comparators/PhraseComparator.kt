package com.leoart.koreanphrasebook.utils.comparators

import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import java.text.Collator
import java.util.*

/**
 * Created by Maryan Onysko (maryan.onysko@gmail.com)
 */

class PhraseComparator : Comparator<EPhrase> {

    override fun compare(p0: EPhrase?, p1: EPhrase?): Int {
        val collator = Collator.getInstance(Locale.UK)
        collator.strength = Collator.PRIMARY
        return collator.compare(p0?.word, p1?.word)
    }
}