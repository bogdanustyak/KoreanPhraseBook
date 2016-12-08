package com.leoart.koreanphrasebook.data.parsers.dialogs

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.DialogsWriter
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Dialog
import java.io.IOException

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class DialogParser(private val context: Context, private val fileName: String) {

    @Throws(IOException::class)
    fun parse(): Dialog {

        val writer = DialogsWriter()
        val dialog = DialogsStream(context.assets.open(fileName)).parse()

        return dialog
    }
}
