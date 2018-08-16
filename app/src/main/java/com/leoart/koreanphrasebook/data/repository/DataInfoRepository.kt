package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.content.SyncInfo
import android.text.TextUtils
import android.widget.ArrayAdapter
import com.google.gson.Gson

import com.leoart.koreanphrasebook.ui.sync.SyncModel

class DataInfoRepository : SharedPrefStorage {

    private var data: List<SyncModel>? = null

    private lateinit var preferences: SharedPreferences

    override fun getData(): List<SyncModel>? {
        data?.let {
            return it
        }
        val userString = preferences.getString(DATA, "")
        if (!TextUtils.isEmpty(userString)) {
            val turns = Gson().fromJson<List<SyncModel>>(userString, SyncModel::class.java)
            data = turns
        }
        return data
    }

    override fun updateData(data: List<SyncModel>) {
        this.data = data
        preferences.edit().putString(DATA, Gson().toJson(data)).apply()
    }

    override fun updateSyncInfo(item: SyncModel) {
        val list = getData()
        list?.toMutableList()?.find { it.name == item.name }?.isSyncNeeded = item.isSyncNeeded

    }


    override fun initialize(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        private const val PREFERENCES_NAME = "sync_data"
        private const val DATA = "data"

        private var instance: DataInfoRepository? = null

        fun getInstance(): DataInfoRepository {
            return if (instance == null) {
                instance = DataInfoRepository()
                instance as DataInfoRepository
            } else {
                instance as DataInfoRepository
            }
        }

    }
}