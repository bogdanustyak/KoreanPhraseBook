package com.leoart.koreanphrasebook.ui.sync

import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.ui.splash.SyncDataRepository
import io.reactivex.Completable

class SyncViewModel(private val syncDataRepository: SyncDataRepository) : ViewModel() {

    fun refreshData(): Completable {
        return syncDataRepository.refreshDB()
    }
}