package com.leoart.koreanphrasebook.data

import com.leoart.koreanphrasebook.data.models.User
import rx.Observable

/**
 * Created by bogdan on 6/22/17.
 */
interface Auth {

    fun register(email: String, password: String): Observable<User>

    fun login(email: String, password: String): Observable<User>

    fun isUserSignedIn(): Boolean

    fun signOut(): Observable<Boolean>
}