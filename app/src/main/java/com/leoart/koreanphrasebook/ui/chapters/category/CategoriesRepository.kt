package com.leoart.koreanphrasebook.ui.chapters.category

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.network.firebase.CategoriesRequest
import com.leoart.koreanphrasebook.data.repository.AppDataBase
import com.leoart.koreanphrasebook.data.repository.DialogsRepository
import com.leoart.koreanphrasebook.data.repository.RefreshableRepository
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import com.leoart.koreanphrasebook.ui.models.Category
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.utils.NetworkChecker
import com.leoart.koreanphrasebook.utils.toCompletable
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class CategoriesRepository(val context: Context) : RefreshableRepository {

    fun getCategories(chapterName: String): Flowable<List<ECategory>> {
        Log.d(DialogsRepository.TAG, "getDictionary")
        return getDataFromDB(chapterName)
                .doOnNext {
                    if (it.isEmpty() && NetworkChecker(context).isNetworkAvailable) {
                        requestFromNetwork()
                    }
                }
    }

    private fun getDataFromDB(categoryName: String): Flowable<List<ECategory>> {
        return AppDataBase.getInstance(context).categoryDao().findBy(categoryName)
    }

    private fun requestFromNetwork() {
        CategoriesRequest().getAllCategories()
                .observeOn(Schedulers.io())
                .subscribe {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(mapToRoomEntity(it))
                    }
                }
    }

    private fun mapToRoomEntity(list: List<Category>): List<ECategory> {
        val eCategory = ArrayList<ECategory>()
        list.forEach {
            it.name["word"]?.let { word ->
                eCategory.add(ECategory(it.id, word, it.inId))
            }
        }
        return eCategory
    }

    private fun saveIntoDB(list: List<ECategory>) {
        localDB().subscribe { db ->
            db.categoryDao().insertAll(*list.toTypedArray())
        }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }

    override fun isEmpty(): Single<SyncModel> {
        return AppDataBase.getInstance(context)
                .categoryDao()
                .count()
                .map {
                    SyncModel(ECategory::class.java.simpleName,it == 0)
                }
    }

    private fun clearDB() {
        AppDataBase.getInstance(context).categoryDao().deleteAll()
    }

    override fun refreshData(): Completable {
        return CategoriesRequest().getAllCategories()
                .observeOn(Schedulers.io())
                .doOnNext {
                    if (it.isNotEmpty()) {
                        clearDB()
                        saveIntoDB(mapToRoomEntity(it))
                    }
                }
                .toCompletable()
    }
}