package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.ChaptersRequest
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.models.Chapter
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.utils.toCompletable
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

/**
 * ChaptersRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class ChaptersRepository(private val context: Context) : CachedRepository<Chapter>, RefreshableRepository {

    override fun getItems(): Flowable<List<Chapter>> {
        Log.d(DialogsRepository.TAG, "getDialogs")
        return getDataFromDB()
                .doOnNext {
                    if (it.isEmpty()) {
                        requestFromNetwork()
                    }
                }
    }

    override fun getDataFromDB(): Flowable<List<Chapter>> {
        return AppDataBase.getInstance(context).chaptersDao().getAll()
                .flatMap {
                    mapChapters(it)
                }
    }

    private fun mapChapters(chapters: List<EChapter>): Flowable<List<Chapter>> {
        return Flowable.fromArray(chapters.map {
            Chapter(
                    it.uid,
                    it.name,
                    it.icon
            )
        })
    }

    override fun requestFromNetwork() {
        ChaptersRequest().getAllChapters()
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
    }

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context).chaptersDao().count().flatMap {
            Single.just(SyncModel(EChapter::class.java.simpleName,it == 0))
        }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).chaptersDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return ChaptersRequest().getAllChapters()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(it)
                    }
                }
                .toCompletable()
    }

    override fun saveIntoDB(items: List<Chapter>) {
        val eChapters = items.map {
            EChapter(it.key, it.name, it.icon)
        }.toTypedArray()

        isEmpty().observeOn(Schedulers.io())
                .subscribe({
                    if (it.isSyncNeeded) {
                        localDB().subscribe {
                            it.chaptersDao().insertAll(*eChapters)
                            DataInfoRepository.getInstance().updateSyncInfo(SyncModel(EChapter::class.java.simpleName, false))
                        }
                    }
                }, {
                    it.printStackTrace()
                })
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}