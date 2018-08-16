package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *  Dialog Entity
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Entity(tableName = "dialog")
data class EDialog(
        val key: String,
        @ColumnInfo(name = "dialogTitle")
        val dialogTitle: String) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
}