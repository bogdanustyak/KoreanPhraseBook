package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteModel
import com.leoart.koreanphrasebook.data.parsers.favourite.FavouriteType
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class FavouriteRepository(val context: Context) {

    fun getData(): Flowable<List<FavouriteModel>> {
        return Flowable.combineLatest(getPhrases(), getVocabulary(), BiFunction { phrase, vocabulary ->
            val list = ArrayList<FavouriteModel>()
            phrase.forEach { list.add(FavouriteModel(it.word, it.translation, it.transcription, FavouriteType.PHRASE)) }
            vocabulary.forEach { list.add(FavouriteModel(it.word, it.definition, null, FavouriteType.VOCABULARY)) }
            list
        })
    }

    private fun getPhrases(): Flowable<List<EPhrase>> {
        return AppDataBase.getInstance(context).phraseDao().getFavourite(true)
    }

    private fun getVocabulary(): Flowable<List<EDictionary>> {
        return AppDataBase.getInstance(context).dictionaryDao().getFavourite("true")
    }

    fun markFavourite(favourite: FavouriteModel) {
        localDB().subscribe { db ->
            if (favourite.type == FavouriteType.VOCABULARY) {
                db.dictionaryDao().findByWord(favourite.word)
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            it.isFavourite = "false"
                            db.dictionaryDao().updateFavorite(it)
                        }

            } else if (favourite.type == FavouriteType.PHRASE) {
                db.phraseDao().findByWord(favourite.word)
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            it.isFavourite = false
                            db.phraseDao().updateFavorite(it)
                        }
            }
        }

    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}