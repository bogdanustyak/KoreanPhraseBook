package com.leoart.koreanphrasebook.ui.favourite

import com.leoart.koreanphrasebook.ui.models.Phrase

/**
 * Created by khrystyna on 8/24/17.
 */
interface FavouriteView {
    fun showPhrases(phrases: List<Phrase>)
    fun removePhrase(position: Int)
}