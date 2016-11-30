package com.leoart.koreanphrasebook.dialogs

import com.leoart.koreanphrasebook.chapters.models.DialogsModel

/**
 * Created by khrystyna on 11/24/16.
 */
interface DialogsView {

    fun showDialogs(chapters: List<DialogsModel>?)
}