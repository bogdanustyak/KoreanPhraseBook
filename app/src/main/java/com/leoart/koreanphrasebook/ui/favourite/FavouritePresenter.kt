package com.leoart.koreanphrasebook.ui.favourite

import com.leoart.koreanphrasebook.data.repository.AppDataBase
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.ui.models.Phrase
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by khrystyna on 8/24/17.
 */
class FavouritePresenter(val view: FavouriteView, val db: AppDataBase) {

    var favourites = ArrayList<Phrase>()

    fun requestData() {

    }

    fun getDataFromVocabulary(): Maybe<List<EDictionary>> {
        return db.dictionaryDao().getFavourite("true")
    }

    fun requestPhrases() {
        getDataFromVocabulary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ favourites ->
                    view.showPhrases(favourites)
                }, { e ->
                    e.printStackTrace()
                })
    }

    fun onFavouriteClicked(position: Int, dictionary: EDictionary) {
        localDB().subscribe {
            if (dictionary.isFavourite.toBoolean()) {
                dictionary.isFavourite = "false"
            } else {
                dictionary.isFavourite = "true"
            }

            it.dictionaryDao().updateFavorite(dictionary)
        }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(db)
                .subscribeOn(Schedulers.io())
    }

}
