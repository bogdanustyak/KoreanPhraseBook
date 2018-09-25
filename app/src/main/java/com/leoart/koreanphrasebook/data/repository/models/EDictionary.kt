package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Entity(tableName = "dictionary")
class EDictionary(
        @ColumnInfo(name = "letter")
        val letter: Char,
        @ColumnInfo(name = "word")
        val word: String,
        @ColumnInfo(name = "definition")
        val definition: String,
        @ColumnInfo(name = "favourite")
        var isFavourite: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long? = null
}