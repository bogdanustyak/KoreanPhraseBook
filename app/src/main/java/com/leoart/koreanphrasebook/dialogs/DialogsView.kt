package com.leoart.koreanphrasebook.dialogs

import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse

/**
 * Created by khrystyna on 11/24/16.
 */
interface DialogsView {

    fun showDialogs(chapters: List<DialogResponse>?)
}