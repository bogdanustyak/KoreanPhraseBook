package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Chapter entity
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Entity(tableName = "chapter")
data class EChapter(
        @ColumnInfo(name = "key")
        val uid: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "icon")
        val icon: String
) {
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
}