package com.leoart.koreanphrasebook.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.utils.resource.Resource
import com.leoart.koreanphrasebook.utils.resource.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashViewModel(private val syncDataRepository: SyncDataRepository) : ViewModel() {


    private var sync: MutableLiveData<Resource<Boolean>>? = null

    fun getSyncInfo(): LiveData<Resource<Boolean>> {
        if (sync == null) {
            sync = MutableLiveData()
            updateSyncData()
        }
        return sync as MutableLiveData<Resource<Boolean>>
    }

    private fun updateSyncData() {
        syncDataRepository.isSyncNeeded()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    sync?.value = Resource(Status.LOADING, null, "Loading")
                }
                .subscribe({
                    sync?.value = Resource(Status.SUCCESS, it, "Loaded")
                }, {
                    it.printStackTrace()
                    sync?.value = Resource(Status.ERROR, null, "Error")
                })
    }

}