package com.leoart.koreanphrasebook.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable

interface RefreshableRepository {


    /**
     * check if local DB is empty
     *
     * @return flowable boolean flag
     */
    fun isEmpty() : Flowable<Boolean>

    /**
     * get data from remote source and save it on local db
     *
     * @return completable as action result
     */
    fun refreshData() : Completable
}