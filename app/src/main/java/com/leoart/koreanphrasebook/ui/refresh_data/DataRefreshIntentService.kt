package com.leoart.koreanphrasebook.ui.refresh_data

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.leoart.koreanphrasebook.data.repository.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class DataRefreshIntentService : IntentService("DataRefreshIntentService") {

    private val repositories = ArrayList<RefreshableRepository>()

    override fun onCreate() {
        super.onCreate()
        repositories.add(AlphabetRepository(applicationContext))
        repositories.add(ChaptersRepository(applicationContext))
        repositories.add(DialogsRepository(applicationContext))
        repositories.add(DictionaryRepository(applicationContext))
        repositories.add(PhraseRepository(applicationContext))
    }

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.getStringExtra(ACTION_TYPE)) {
            CHECK_IF_DB_IS_EMPTY -> checkDB()
            REFRESH_DB -> refreshDB()
        }
    }

    private fun refreshDB() {
        val refreshChain = repositories.map { it.refreshData() }
        Completable.merge(refreshChain)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    sendResultForRefresh()
                }, {
                    sendRefreshError()
                })
    }

    private fun sendRefreshError() {
        val broadcastIntent = Intent()
        broadcastIntent.action = ACTION_RESP
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT)
        broadcastIntent.putExtra(ACTION_TYPE, REFRESH_DB_ERROR)
        sendBroadcast(broadcastIntent)
        stopSelf()
    }

    private fun sendResultForRefresh() {
        val broadcastIntent = Intent()
        broadcastIntent.action = ACTION_RESP
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT)
        broadcastIntent.putExtra(ACTION_TYPE, REFRESH_DB)
        sendBroadcast(broadcastIntent)
        stopSelf()
    }

    private fun checkDB() {
        val checks = repositories.map { it.isEmpty() }
        Single.merge(checks).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.find { it == true } != null) {
                        sendResultForDBCheck(true)
                    } else {
                        sendResultForDBCheck(false)
                    }
                }, {
                    sendRefreshError()
                })
    }

    private fun sendResultForDBCheck(isEmpty: Boolean) {
        val broadcastIntent = Intent()
        broadcastIntent.action = ACTION_RESP
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT)
        broadcastIntent.putExtra(ACTION_TYPE, CHECK_IF_DB_IS_EMPTY)
        broadcastIntent.putExtra(IS_EMPTY, isEmpty)
        sendBroadcast(broadcastIntent)
        stopSelf()
    }

    companion object {
        const val ACTION_TYPE = "action type"
        const val CHECK_IF_DB_IS_EMPTY = "check db"
        const val IS_EMPTY = "is empty"
        const val REFRESH_DB = "refresh db"
        const val REFRESH_DB_ERROR = "refresh db error"
        const val ACTION_RESP = "com.leoart.koreanphrasebook.intent.action.MESSAGE_PROCESSED"
    }
}