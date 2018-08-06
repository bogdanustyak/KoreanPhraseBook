package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.ChaptersRequest
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import com.leoart.koreanphrasebook.ui.models.Chapter
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
                    if(it.isEmpty()){
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
                .subscribe{
                    clearDB()
                    saveIntoDB(it)
                }
    }

    override fun isEmpty(): Single<Boolean> {
        return AppDataBase.getInstance(context).chaptersDao().count().flatMap {
            Single.just(it == 0)
        }
    }

    private fun clearDB(){
        AppDataBase.getInstance(context).chaptersDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return Completable.create { emitter ->
            ChaptersRequest().getAllChapters()
                    .observeOn(Schedulers.io())
                    .subscribe{
                        clearDB()
                        saveIntoDB(it)
                        emitter.onComplete()
                    }
        }
    }

    override fun saveIntoDB(items: List<Chapter>) {
        val eChapters = items.map {
            EChapter(it.key, it.name, it.icon)
        }.toTypedArray()
        localDB().subscribe {
            it.chaptersDao().insertAll(*eChapters)
        }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}