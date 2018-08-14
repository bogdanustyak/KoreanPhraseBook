package com.leoart.koreanphrasebook.ui.sync

import com.google.gson.annotations.SerializedName

class SyncModel(
        @SerializedName("name")
        val name: String,
        @SerializedName("sync")
        var isSyncNeeded: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != SyncModel::class.java) return false
        other as SyncModel
        if (other.name == this.name && other.isSyncNeeded == this.isSyncNeeded)
            return true
        return false
    }
}