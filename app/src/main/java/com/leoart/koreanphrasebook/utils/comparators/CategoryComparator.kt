package com.leoart.koreanphrasebook.utils.comparators

import com.leoart.koreanphrasebook.data.repository.models.ECategory
import java.text.Collator
import java.util.*

/**
 * Created by Maryan Onysko (maryan.onysko@gmail.com)
 */

class CategoryComparator : Comparator<ECategory> {

    override fun compare(p0: ECategory?, p1: ECategory?): Int {
        val collator = Collator.getInstance(Locale.UK)
        collator.strength = Collator.PRIMARY
        return collator.compare(p0?.category, p1?.category)
    }
}