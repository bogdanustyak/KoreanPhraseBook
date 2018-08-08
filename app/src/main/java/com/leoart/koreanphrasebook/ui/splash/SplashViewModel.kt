package com.leoart.koreanphrasebook.ui.splash

import android.arch.lifecycle.ViewModel
import io.reactivex.Single

class SplashViewModel(private val syncDataRepository: SyncDataRepository) : ViewModel() {

    fun openNextScreen(): Single<Boolean> {
        return syncDataRepository.isSyncNeeded()
    }

}