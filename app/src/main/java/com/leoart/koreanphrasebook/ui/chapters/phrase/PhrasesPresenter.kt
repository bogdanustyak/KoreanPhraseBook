package com.leoart.koreanphrasebook.ui.chapters.phrase

import com.leoart.koreanphrasebook.data.network.firebase.dictionary.PhrasesRequest
import com.leoart.koreanphrasebook.data.realm.FavouriteData
import com.leoart.koreanphrasebook.ui.models.Phrase
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class PhrasesPresenter(var view: PhrasesView?, var category: String) {

    var phrases = ArrayList<Phrase>()

    fun requestPhrases() {
        PhrasesRequest().getPhrases(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    this.phrases = mergeWithFavourite(list)
                    view?.showPhrases(phrases)
                })
    }

    private fun mergeWithFavourite(list: List<Phrase>): ArrayList<Phrase> {
        val favouriteKeys = FavouriteData().getFavouritePhrases()
        val mergedPhrases = ArrayList<Phrase>()
        for (phrase in list) {
            favouriteKeys
                    .filter { it.key == phrase.key }
                    .forEach { phrase.isFavourite = true }
            mergedPhrases.add(phrase)
        }
        return mergedPhrases
    }

    fun onFavouriteClicked(position: Int) {
        val isSelectedFavourite = phrases[position].isFavourite
        if (isSelectedFavourite) {
            removeFromFavourite(phrases[position])
        } else {
            addToFavourite(phrases[position])
        }
        phrases[position].isFavourite = !isSelectedFavourite
        view?.updatePhrase(position, phrases[position])
    }

    private fun addToFavourite(key: Phrase) {
        FavouriteData().addPhraseToFavourite(key)
    }

    private fun removeFromFavourite(key: Phrase) {
        FavouriteData().removePhraseFromFavourite(key )
    }
}