package com.leoart.koreanphrasebook.ui.dialogs.dialog

import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic

/**
 * Created by bogdan on 11/14/16.
 */

interface DialogMessagesView {
    fun showReplics(replics: List<Replic>)
}
