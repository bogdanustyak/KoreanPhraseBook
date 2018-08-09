package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "phrase")
class EPhrase(
        @ColumnInfo(name = "word")
        val word: String,
        @ColumnInfo(name = "translation")
        val translation: String,
        @ColumnInfo(name = "transcription")
        val transcription: String,
        @ColumnInfo(name = "favourite")
        var isFavourite: Boolean = false,
        @ColumnInfo(name = "category")
        val category: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
}