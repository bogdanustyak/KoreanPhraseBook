package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.leoart.koreanphrasebook.data.repository.models.ENote
import io.reactivex.Flowable

@Dao
interface NotesDao {

    @Query("SELECT * from note")
    fun getAll() : Flowable<List<ENote>>

    @Insert
    fun insertAll(vararg notes : ENote)


}