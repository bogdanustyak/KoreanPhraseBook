package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "letter")
data class ELetter(
    @PrimaryKey
    var uid : String,
    @ColumnInfo(name = "korean_letter")
    var koreanLetter : String,
    @ColumnInfo(name = "translate_letter")
    var translateLetter : String
)