package com.leoart.koreanphrasebook.ui.dialogs

import android.content.Context
import com.leoart.koreanphrasebook.data.repository.DialogsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by khrystyna on 11/24/16.
 */
class DialogsPresenter(private val view: DialogsView?,
                       context: Context) {

    private val dialogsRepository = DialogsRepository(context)

    fun requestDialogs() {
        dialogsRepository.getDialogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dialogs ->
                    view?.showDialogs(dialogs)
                }, { throwable ->
                    throw UnsupportedOperationException("not implemented")
                })
    }
}
