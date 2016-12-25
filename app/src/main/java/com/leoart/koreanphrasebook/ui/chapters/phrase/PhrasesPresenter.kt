package com.leoart.koreanphrasebook.ui.chapters.phrase

import com.leoart.koreanphrasebook.data.network.firebase.dictionary.PhrasesRequest
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class PhrasesPresenter(var view: PhrasesView?, var category: String) {

    fun requestPhrases() {
        PhrasesRequest().getPhrases(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    phrases ->
                    view?.showPhrases(phrases)
                })
    }
}