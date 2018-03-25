package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import io.reactivex.Flowable
import io.reactivex.Maybe


/**
 * DictionaryDao
 * Room dao
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Dao
interface DictionaryDao {

    @Query("SELECT * FROM dictionary")
    fun getAll(): Flowable<List<EDictionary>>

    @Query("SELECT * FROM dictionary WHERE word LIKE :query OR definition LIKE :query")
    fun findBy(query: String): Maybe<List<EDictionary>>

    @Insert
    fun insertAll(vararg dict: EDictionary)

    @Delete
    fun delete(dict: EDictionary)
}