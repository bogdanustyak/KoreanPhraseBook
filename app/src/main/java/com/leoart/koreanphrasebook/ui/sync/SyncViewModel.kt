package com.leoart.koreanphrasebook.ui.sync

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.ui.splash.SyncDataRepository
import com.leoart.koreanphrasebook.utils.resource.Resource
import com.leoart.koreanphrasebook.utils.resource.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SyncViewModel(private val syncDataRepository: SyncDataRepository) : ViewModel() {

    private var sync: MutableLiveData<Resource<Boolean>>? = null

    fun getSyncInfo(): LiveData<Resource<Boolean>> {
        if (sync == null) {
            sync = MutableLiveData()
            syncData()
        }
        return sync as MutableLiveData<Resource<Boolean>>
    }

    private fun syncData() {
        syncDataRepository.refreshDB()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    sync?.value = Resource(Status.LOADING, null, "Loading")
                }
                .subscribe({
                    sync?.value = Resource(Status.SUCCESS, true, "Loaded")
                }, {
                    sync?.value = Resource(Status.ERROR, null, it.message)
                    it.printStackTrace()
                })
    }
}