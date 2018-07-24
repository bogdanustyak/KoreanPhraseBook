package com.leoart.koreanphrasebook.ui.favourite

import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.ui.models.Phrase

/**
 * Created by khrystyna on 8/24/17.
 */
interface FavouriteView {
    fun showPhrases(phrases: List<EDictionary>)
    fun removePhrase(position: Int)
}