package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.ui.sync.SyncModel

interface SharedPrefStorage {
    fun initialize(context: Context)
    fun updateData(data: List<SyncModel>)
    fun getData(): List<SyncModel>?
}
