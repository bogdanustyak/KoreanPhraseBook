package com.leoart.koreanphrasebook.data.network.firebase

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by bogdan on 11/6/16.
 */
abstract class FireBaseRequest {
    protected val dataBase = FirebaseDatabase.getInstance()
    protected val dataBaseRef = dataBase.reference

}