package com.leoart.koreanphrasebook.dialogs

import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by khrystyna on 11/24/16.
 */
class DialogsPresenter(val view: DialogsView?) {

    fun requestDialogs() {
        DialogsRequest()
                .getAllDialogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    dialogs ->
                    view?.showDialogs(dialogs)
                })
    }
}
