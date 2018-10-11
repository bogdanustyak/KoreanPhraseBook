package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.dictionary.PhrasesRequest
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.models.Phrase
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.utils.NetworkChecker
import com.leoart.koreanphrasebook.utils.toCompletable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class PhraseRepository(val context: Context) : RefreshableRepository {

    fun getPhrases(categoryName: String): Flowable<List<EPhrase>> {
        Log.d(TAG, "getDictionary")
        return getDataFromDB(categoryName)
                .doOnNext {
                    if (it.isEmpty() && NetworkChecker(context).isNetworkAvailable) {
                        requestFromNetwork()
                    }
                }
    }

    fun markFavourite(phrase: EPhrase): Completable {
        return Completable.create { emitter ->
            localDB().subscribe({
                try {
                    it.phraseDao().updateFavorite(phrase)
                    emitter.onComplete()
                } catch (e: Exception) {
                    emitter.onError(e)
                }
            }, {
                emitter.onError(it)
            })
        }
    }

    private fun getDataFromDB(categoryName: String): Flowable<List<EPhrase>> {
        return AppDataBase.getInstance(context).phraseDao().getByCategory(categoryName)
    }

    private fun requestFromNetwork() {
        PhrasesRequest().getPhrases()
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(mapToRoomEntity(it))
                    }
                }
    }

    private fun mapToRoomEntity(list: List<Phrase>): List<EPhrase> {
        val ePhrases = ArrayList<EPhrase>()
        list.forEach {
            ePhrases.add(EPhrase(it.word, it.translation, it.transcription, it.isFavourite, it.key))
        }
        return ePhrases
    }

    private fun saveIntoDB(list: List<EPhrase>) {
        isEmpty().observeOn(Schedulers.io())
                .flatMap {
                    val syncResult: Single<Boolean>
                    if (it.isSyncNeeded) {
                        syncResult = localDB().flatMap { db ->
                            db.phraseDao().insertAll(*list.toTypedArray())
                            DataInfoRepository.getInstance().updateSyncInfo(SyncModel(EPhrase::class.java.simpleName, false))
                            Observable.just(true)
                        }.single(false)
                        return@flatMap syncResult
                    } else {
                        return@flatMap Single.just(false)
                    }
                }.subscribe({
                    Log.d(TAG, "data saved")
                }, {
                    it.printStackTrace()
                })
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context)
                .phraseDao()
                .count()
                .map {
                    SyncModel(EPhrase::class.java.simpleName, it == 0)
                }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).phraseDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return PhrasesRequest().getPhrases()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(mapToRoomEntity(it))
                    }
                }
                .toCompletable()
    }

    companion object {
        const val TAG = "PhraseRepository"
    }
}