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
//        PhrasesRequest().getPhrases()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ list ->
//                    for (item in list) {
//                        favouriteKeys
//                                .filter { it == item.key }
//                                .forEach { favourites.add(item) }
//                        view.showPhrases(list)
//                    }
//                })
    }

    fun onFavouriteClicked(position: Int) {
        FavouriteData().removePhraseFromFavourite(favourites[position])
        view.removePgrase(position, favourites[position])
    }

}