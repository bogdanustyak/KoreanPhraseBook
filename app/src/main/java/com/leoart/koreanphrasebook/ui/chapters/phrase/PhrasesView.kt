package com.leoart.koreanphrasebook.ui.chapters.phrase

import com.leoart.koreanphrasebook.ui.chapters.models.Phrase

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface PhrasesView {
    fun showPhrases(phrases: List<Phrase>)
}