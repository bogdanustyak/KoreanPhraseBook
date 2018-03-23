package com.leoart.koreanphrasebook.data.repository

import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import io.reactivex.Observable
import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import com.leoart.koreanphrasebook.ui.models.Chapter
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.MaybeSource
import io.reactivex.internal.operators.maybe.MaybeFromAction

/**
 * SearchRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SearchRepository(val searchQuery: String, val context: Context) {

//    fun searchDict(query: String): Observable<Dictionary> {
//        return Observable.empty()
////        return dictionaryRepository
////                .getDictionary()
//    }

    fun searchChapters(): Maybe<EChapter>{
        return AppDataBase.getInstance(context).chaptersDao().findByName(searchQuery)
    }

    fun getChapters(): Maybe<List<Chapter>>{
        return searchChapters().flatMap { chapters ->
            return@flatMap MaybeSource<List<Chapter>> { chapters }
        }
    }

    fun searchDialogs(): Maybe<EDialog>{
        return AppDataBase.getInstance(context).dialogDao().findByName(searchQuery)
    }

    fun getDialogs(): Maybe<List<DialogResponse>>{
        return searchDialogs().flatMap { dialogs ->
            return@flatMap MaybeSource<List<DialogResponse>> { dialogs }
        }
    }

    fun searchDictionary(): Maybe<EDictionary>{
        return AppDataBase.getInstance(context).dictionaryDao().findBy(searchQuery)
    }

    fun getDiactionary(): Maybe<List<Dictionary>>{
        return searchDictionary().flatMap{dictionary ->
            return@flatMap MaybeSource<List<Dictionary>> { dictionary }
        }
    }

    fun searchReplic(): Maybe<EReplic>{
        return AppDataBase.getInstance(context).replicsDao().findByReplic(searchQuery)
    }

    fun getReplic(): Maybe<List<Replic>>{
        return searchReplic().flatMap { replic ->
            return@flatMap MaybeSource<List<Replic>> { replic }
        }
    }

}