package com.leoart.koreanphrasebook.data.parsers.dialogs

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import java.io.IOException

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class DialogParser(private val context: Context, private val fileName: String) {

    @Throws(IOException::class)
    fun parse() {

        val dialog = DialogsStream(context.assets.open(fileName)).parse()
        DialogsRequest().writeDialogReplics(dialog)
    }
}
