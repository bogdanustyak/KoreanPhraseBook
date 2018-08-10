package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "category")
data class ECategory(
        @ColumnInfo(name = "ikey")
        val key: String,
        @ColumnInfo(name = "word")
        val category: String) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
}