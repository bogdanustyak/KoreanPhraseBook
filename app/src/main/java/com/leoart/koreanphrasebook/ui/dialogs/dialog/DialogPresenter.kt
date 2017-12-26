package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.content.Context
import com.leoart.koreanphrasebook.data.repository.DialogsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class DialogPresenter(private val view: DialogMessagesView,
                      private val context: Context) {
    private val dialogsRepository = DialogsRepository(context)

    fun getReplics(dialogID: String) {
        dialogsRepository.getAllDialogReplics(dialogID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ replics ->
                    replics.let {
                        view.showReplics(replics)
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    throw UnsupportedOperationException("not implemented")
                })
    }
}