package com.leoart.koreanphrasebook.data.realm

import io.realm.Realm

/**
 * Created by khrystyna on 8/24/17.
 */
class FavouriteData {

    private val realm = Realm.getDefaultInstance()

    fun addPhraseToFavourite(phraseKey: String) {
        val favouriteRealmModel = FavouriteModel(phraseKey)
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(favouriteRealmModel)
        realm.commitTransaction()
        realm.close()
    }

    fun removePhraseFromFavourite(phraseKey: String) {
        val favouriteRealmModel = FavouriteModel(phraseKey)
        realm.beginTransaction()
        favouriteRealmModel.deleteFromRealm()
        realm.commitTransaction()
        realm.close()
    }

    fun getFavouritePhrases(): ArrayList<String> {
        val phraseKeys = ArrayList<String>()
        realm.beginTransaction()
        realm.where(FavouriteModel::class.java)
                .findAll()
                .mapTo(phraseKeys) { it ->
                    it.phraseKey
                }
        realm.commitTransaction()
        realm.close()
        return phraseKeys
    }


}