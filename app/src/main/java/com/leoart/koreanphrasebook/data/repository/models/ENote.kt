package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note")
data class ENote(
        @PrimaryKey
        val uid : String,
        @ColumnInfo(name = "title")
        val title : String,
        @ColumnInfo(name = "description")
        val description : String
)