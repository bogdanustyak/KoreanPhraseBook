package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.*
import com.leoart.koreanphrasebook.data.repository.models.ENote
import io.reactivex.Flowable

@Dao
interface NotesDao {

    @Query("SELECT * from note")
    fun getAll() : Flowable<List<ENote>>

    @Insert
    fun insertAll(vararg notes : ENote)

    @Update
    fun updateNote(note : ENote)

    @Delete
    fun deleteNote(note : ENote)

}