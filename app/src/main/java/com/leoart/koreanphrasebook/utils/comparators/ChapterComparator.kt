package com.leoart.koreanphrasebook.utils.comparators

import com.leoart.koreanphrasebook.data.repository.models.EChapter
import java.text.Collator
import java.util.*

/**
 * Created by Maryan Onysko (maryan.onysko@gmail.com)
 */

class ChapterComparator : Comparator<EChapter> {

    override fun compare(p0: EChapter?, p1: EChapter?): Int {
        val collator = Collator.getInstance(Locale.UK)
        collator.strength = Collator.PRIMARY
        return collator.compare(p0?.name, p1?.name)
    }
}