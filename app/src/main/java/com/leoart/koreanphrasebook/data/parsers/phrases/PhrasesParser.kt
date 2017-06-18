package com.leoart.koreanphrasebook.data.parsers.phrases

import android.content.Context
import com.leoart.koreanphrasebook.ui.models.Phrase
import java.io.IOException

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class PhrasesParser(private val context: Context, private val fileName: String) {

    @Throws(IOException::class)
    fun parse(): List<Phrase> {
        return PhraseStream(context.assets.open(fileName)).parse()
    }
}
