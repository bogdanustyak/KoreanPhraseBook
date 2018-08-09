package com.leoart.koreanphrasebook.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashViewModel(private val syncDataRepository: SyncDataRepository) : ViewModel() {


    private var sync: MutableLiveData<Boolean>? = null

    fun getSyncInfo(): LiveData<Boolean> {
        if (sync == null) {
            sync = MutableLiveData()
            updateSyncData()
        }
        return sync as MutableLiveData<Boolean>
    }

    private fun updateSyncData() {
        syncDataRepository.isSyncNeeded()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sync?.value = it
                }, {
                    it.printStackTrace()
                })
    }

}