package com.leoart.koreanphrasebook.data.repository.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Replic entity
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Entity (tableName = "replic")
data class EReplic(
        val uid: String,
        val korean: String,
        val ukrainian: String,
        val number: Int
){
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
}