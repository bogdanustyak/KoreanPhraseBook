package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.ChaptersRequest
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import com.leoart.koreanphrasebook.ui.models.Chapter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * ChaptersRepository
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class ChaptersRepository(private val context: Context) : CachedRepository<Chapter> {

    override fun getItems(): Flowable<List<Chapter>> {
        Log.d(DialogsRepository.TAG, "getDialogs")
        return getDataFromDB()
                .flatMap { chapters ->
                    if (chapters.isNotEmpty()) {
                        return@flatMap Flowable.fromArray(chapters)
                    } else {
                        return@flatMap requestFromNetwork()
                                .toFlowable(BackpressureStrategy.LATEST)
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

    override fun requestFromNetwork(): Observable<List<Chapter>> {
        return ChaptersRequest().getAllChapters()
                .flatMap {
                    saveIntoDB(it)
                }
    }

    override fun saveIntoDB(chapters: List<Chapter>): Observable<List<Chapter>> {
        return Observable.create { emitter ->
            val eChapters = chapters.map {
                EChapter(it.key, it.name, it.icon)
            }.toTypedArray()
            localDB().subscribe {
                it.chaptersDao().insertAll(*eChapters)
                emitter.onNext(chapters)
            }
        }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}