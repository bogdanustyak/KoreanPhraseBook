package com.leoart.koreanphrasebook.ui.splash

import android.content.Context
import com.leoart.koreanphrasebook.data.repository.*
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesRepository
import com.leoart.koreanphrasebook.ui.dialogs.dialog.DiealogRepository
import io.reactivex.Completable
import io.reactivex.Single
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
                        DataInfoRepository.getInstance().updateData(it)
                        if (it.find { it.isSyncNeeded == true } != null) {
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