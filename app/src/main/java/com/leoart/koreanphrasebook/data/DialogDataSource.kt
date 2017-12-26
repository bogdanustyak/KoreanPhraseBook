package com.leoart.koreanphrasebook.data

import com.leoart.koreanphrasebook.ui.models.Dialogs
import io.reactivex.Flowable

/**
 * DialogDataSource
 *
 * Access point for managing dialogs data.
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface DialogDataSource {

    /**
     * Gets all dialogs from the data source.
     *
     * @return the list of dialogs from the data source.
     */
    fun getAllDialogs(): Flowable<List<Dialogs>>

    /**
     * Inserts the dialog into the data source, or, if this is an existing dialog, updates it.
     *
     * @param dialog the user to be inserted or updated.
     */
    fun insertOrUpdateUser(dialog: Dialogs)

}