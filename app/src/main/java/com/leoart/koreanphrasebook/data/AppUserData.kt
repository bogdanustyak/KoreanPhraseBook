package com.leoart.koreanphrasebook.data

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by khrystyna on 8/24/17.
 */
class AppUserData(context: Context) {
    var prefs: SharedPreferences? = null

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    public fun addPhraseToFavourite(phraseKey: String) {
        prefs?.edit()
                ?.putString(PHRASE_KEY, phraseKey)
                ?.apply()
    }

    fun getFavouritePhrases(): ArrayList<String> {
        val phraseKeys = ArrayList<String>()

        return phraseKeys
    }

    companion object {
        const val PHRASE_KEY = "phrase_key"
    }
}