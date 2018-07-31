package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import io.reactivex.Flowable
import java.util.*

@Dao
interface LetterDao {

    @Query("SELECT * FROM letter")
    fun fetchAll() : Flowable<List<ELetter>>

    @Insert
    fun insertAll(vararg letters : ELetter)
}