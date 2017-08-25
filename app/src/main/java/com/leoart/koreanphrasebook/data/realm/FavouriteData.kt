package com.leoart.koreanphrasebook.data.realm

import com.leoart.koreanphrasebook.ui.models.Phrase
import io.realm.Realm

/**
 * Created by khrystyna on 8/24/17.
 */
class FavouriteData {

    private val realm = Realm.getDefaultInstance()

    fun addPhraseToFavourite(phrase: Phrase) {
        val favouriteRealmModel = FavouriteModel(phrase)
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(favouriteRealmModel)
        realm.commitTransaction()
        realm.close()
    }

    fun removePhraseFromFavourite(phrase: Phrase) {
        realm.beginTransaction()
        val favouriteRealmModel = realm.where(FavouriteModel::class.java)
                .equalTo("phraseKey", phrase.key)
                .findFirst()
        favouriteRealmModel.deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    fun getFavouritePhrases(): ArrayList<Phrase> {
        val phrases = ArrayList<Phrase>()
        realm.beginTransaction()
        realm.where(FavouriteModel::class.java)
                .findAll()
                .mapTo(phrases) { it ->
                    it.toPhase()
                }
        realm.commitTransaction()
        realm.close()
        return phrases
    }


}