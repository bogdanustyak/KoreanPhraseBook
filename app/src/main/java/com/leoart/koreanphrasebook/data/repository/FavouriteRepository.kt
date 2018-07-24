package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.parsers.favourite.Favourite
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteType
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class FavouriteRepository(val context: Context) {

    fun getData(): Flowable<List<Favourite>> {
        return Flowable.combineLatest(getPhrases(), getVocabulary(), BiFunction { phrase, vocabulary ->
            val list = ArrayList<Favourite>()
            phrase.forEach { list.add(Favourite(null, it.word, it.translation, it.transcription, it.isFavourite, it.category, FavouriteType.PHRASE)) }
            vocabulary.forEach { list.add(Favourite(it.letter, it.word, it.definition, null, it.isFavourite.toBoolean(), null, FavouriteType.VOCABULARY)) }
            list
        })
    }

    private fun getPhrases(): Flowable<List<EPhrase>> {
        return AppDataBase.getInstance(context).phraseDao().getFavourite(true)
    }

    private fun getVocabulary(): Flowable<List<EDictionary>> {
        return AppDataBase.getInstance(context).dictionaryDao().getFavourite("true")
    }

    fun markFavourite(favourite: Favourite) {
        favourite.isFavourite = !favourite.isFavourite
        localDB().subscribe { db ->
            if (favourite.type == FavouriteType.VOCABULARY) {
                favourite.letter?.let {
                    db.dictionaryDao().updateFavorite(EDictionary(favourite.letter, favourite.word, favourite.translation, favourite.isFavourite.toString()))
                }
            } else if (favourite.type == FavouriteType.PHRASE) {
                favourite.transcription?.let { transcription ->
                    favourite.category?.let { category ->
                        db.phraseDao().updateFavorite(EPhrase(favourite.word, favourite.translation, transcription, favourite.isFavourite, category))
                    }
                }

            }
        }
    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}