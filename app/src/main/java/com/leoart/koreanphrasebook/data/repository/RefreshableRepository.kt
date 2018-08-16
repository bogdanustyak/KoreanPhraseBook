package com.leoart.koreanphrasebook.data.repository

import com.leoart.koreanphrasebook.ui.sync.SyncModel
import io.reactivex.Completable
import io.reactivex.Single

interface RefreshableRepository {


    /**
     * check if local DB is empty
     *
     * @return flowable boolean flag
     */
    fun isEmpty(): Single<SyncModel>

    /**
     * get data from remote source and save it on local db
     *
     * @return completable as action result
     */
    fun refreshData() : Completable
}