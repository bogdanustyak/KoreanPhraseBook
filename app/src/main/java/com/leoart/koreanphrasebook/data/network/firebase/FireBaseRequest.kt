package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by bogdan on 11/6/16.
 */
abstract class FireBaseRequest {
    protected val mDataBase = FirebaseDatabase.getInstance()
    protected val mDataBaseRef = mDataBase.reference

}