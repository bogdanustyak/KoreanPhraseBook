package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.*
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

    @Query("SELECT * FROM dictionary WHERE word LIKE :query")
    fun findByWord(query: String): Maybe<EDictionary>

    @Query("SELECT * FROM dictionary WHERE favourite LIKE :query")
    fun getFavourite(query: String): Flowable<List<EDictionary>>

    @Update
    fun updateFavorite(dict: EDictionary)

    @Insert
    fun insertAll(vararg dict: EDictionary)

    @Delete
    fun delete(dict: EDictionary)
}