package com.leoart.koreanphrasebook.utils

import io.reactivex.Completable
import io.reactivex.Observable

fun <T> Observable<T>.toCompletable(): Completable {
    return flatMapCompletable { Completable.complete() }
}
