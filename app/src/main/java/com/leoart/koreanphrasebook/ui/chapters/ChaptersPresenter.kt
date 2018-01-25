package com.leoart.koreanphrasebook.ui.chapters

import android.content.Context
import com.leoart.koreanphrasebook.data.repository.ChaptersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by bogdan on 11/6/16.
 */
class ChaptersPresenter(val view: ChaptersView?, context: Context) {

    private val repository = ChaptersRepository(context)

    fun requestChapters() {
        repository.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ chapters ->
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