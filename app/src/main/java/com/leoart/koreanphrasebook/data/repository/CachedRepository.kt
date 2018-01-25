package com.leoart.koreanphrasebook.data.repository

import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * CachedRepository
 *
 * Requests data from network or local storage
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

interface CachedRepository<ITEM> {

    /**
     * retrieves items from local storage ot rest
     *
     * @return flowable list of items
     */
    fun getItems(): Flowable<List<ITEM>>

    /**
     * retrieves data from local DB
     *
     * @return flowable list of items
     */
    fun getDataFromDB(): Flowable<List<ITEM>>

    /**
     * request data from network
     *
     * @return observable list of items
     */
    fun requestFromNetwork(): Observable<List<ITEM>>

    /**
     * save data to local storage
     *
     * @return observable list of saved items
     */
    fun saveIntoDB(items: List<ITEM>): Observable<List<ITEM>>
}