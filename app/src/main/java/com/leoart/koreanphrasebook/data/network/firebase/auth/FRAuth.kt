package com.leoart.koreanphrasebook.data.network.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.leoart.koreanphrasebook.data.Auth
import com.leoart.koreanphrasebook.data.models.User
import rx.Observable

/**
 * Created by bogdan on 6/22/17.
 */
class FRAuth : Auth {

    private val LOGIN_ERROR = "Could not login"
    private val REG_ERROR = "Could not register."
    private var firAuth: FirebaseAuth? = null

    init {
        this.firAuth = FirebaseAuth.getInstance()
    }

    override fun register(email: String, password: String): Observable<User> {
        return Observable.create({ emitter ->
            firAuth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            val mail = firAuth?.currentUser?.email
                            if (mail != null) {
                                emitter.onNext(User(mail))
                                emitter.onCompleted()
                            } else {
                                emitter.onError(Throwable(REG_ERROR))
                            }
                        } else {
                            emitter.onError(Throwable(REG_ERROR))
                        }
                    }
        })
    }

    override fun login(email: String, password: String): Observable<User> {
        return Observable.create({ emitter ->
            firAuth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = firAuth?.currentUser
                            if (user != null) {
                                emitter.onNext(User(email))
                                emitter.onCompleted()
                            } else {
                                emitter.onError(Throwable(LOGIN_ERROR))
                            }
                        } else {
                            emitter.onError(Throwable(LOGIN_ERROR))
                        }
                    }
        })
    }

    override fun isUserSignedIn(): Boolean {
        return firAuth?.currentUser != null
    }

    override fun signOut(): Observable<Boolean> {
        firAuth?.signOut()
        return Observable.just(true)
    }
}