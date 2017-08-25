package com.leoart.koreanphrasebook.ui.favourite

import com.leoart.koreanphrasebook.data.realm.FavouriteData
import com.leoart.koreanphrasebook.ui.models.Phrase

/**
 * Created by khrystyna on 8/24/17.
 */
class FavouritePresenter(val view: FavouriteView) {

    var favourites = ArrayList<Phrase>()

    fun requestPhrases() {
        favourites = FavouriteData().getFavouritePhrases()
        view.showPhrases(favourites)
    }

    fun onFavouriteClicked(position: Int) {
        FavouriteData().removePhraseFromFavourite(favourites[position])
        favourites.removeAt(position)
        view.removePhrase(position)
    }

}