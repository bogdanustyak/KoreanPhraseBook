package com.leoart.koreanphrasebook.data.network.firebase.dialogs.models

import java.util.ArrayList

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Dialog @JvmOverloads constructor(private val messages: MutableList<Replic> = ArrayList<Replic>()) {

    @Throws(IllegalArgumentException::class)
    fun addMessage(message: Replic?) {
        if (message == null) {
            throw IllegalArgumentException("message should not be empty")
        }
        this.messages.add(message)
    }

    fun getMessages(): List<Replic> {
        return messages
    }
}
