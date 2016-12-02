package com.leoart.koreanphrasebook.dialogs

import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by khrystyna on 11/24/16.
 */
class DialogsPresenter(val view: DialogsView?) {

    fun requestDialogs() {
        DialogsRequest().getAllDialogNames()
                .subscribe(object : Subscriber<List<DialogResponse>>() {
                    override fun onError(e: Throwable?) {
                        throw UnsupportedOperationException("not implemented")
                    }

                    override fun onCompleted() {

                    }

                    override fun onNext(dialogs: List<DialogResponse>?) {
                        view?.showDialogs(dialogs)
                    }

                })
    }
}
