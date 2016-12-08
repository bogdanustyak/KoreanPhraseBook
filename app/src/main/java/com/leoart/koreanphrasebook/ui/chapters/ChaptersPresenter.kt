package com.leoart.koreanphrasebook.ui.chapters

import com.leoart.koreanphrasebook.data.network.firebase.ChaptersRequest
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by bogdan on 11/6/16.
 */
class ChaptersPresenter(val view: ChaptersView?) {

    fun requestChapters() {
        ChaptersRequest().getAllChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    chapters ->
                    view?.showChapters(chapters)
                })
//        .subscribe(object : Subscriber<List<Chapter>>(){
//            override fun onError(e: Throwable?) {
//                throw UnsupportedOperationException("not implemented")
//            }
//
//            override fun onCompleted() {
//                throw UnsupportedOperationException("not implemented")
//            }
//
//            override fun onNext(t: List<Chapter>?) {
//                throw UnsupportedOperationException("not implemented")
//            }
//
//        })

    }

}