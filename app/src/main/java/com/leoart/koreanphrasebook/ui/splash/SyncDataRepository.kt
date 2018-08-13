package com.leoart.koreanphrasebook.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.parsers.vocabulary.Dictionary
import com.leoart.koreanphrasebook.data.repository.*
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesRepository
import com.leoart.koreanphrasebook.ui.dialogs.dialog.DiealogRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SyncDataRepository(context: Context) {

    private val repositories = ArrayList<RefreshableRepository>()

    init {
        repositories.add(AlphabetRepository(context))
        repositories.add(ChaptersRepository(context))
        repositories.add(DialogsRepository(context))
        repositories.add(DictionaryRepository(context))
        repositories.add(PhraseRepository(context))
        repositories.add(CategoriesRepository(context))
        repositories.add(DiealogRepository(context))
    }

    fun isSyncNeeded(): Single<Boolean> {
        val checks = repositories.map { it.isEmpty() }
        return Single.create<Boolean> { singleEmitter ->
            Single.merge(checks).toList()
                    .observeOn(Schedulers.io())
                    .subscribe({
                        if (it.find { it == true } != null) {
                            singleEmitter.onSuccess(false)
                        } else {
                            singleEmitter.onSuccess(true)
                        }
                    }, {
                        singleEmitter.onError(it)
                    })
        }
    }

    fun refreshDB(): Completable {
        val refreshChain = repositories.map { it.refreshData() }
        return Completable.merge(refreshChain)
    }

}