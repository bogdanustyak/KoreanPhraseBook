package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.*
import com.leoart.koreanphrasebook.data.repository.models.EPhrase
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * DictionaryDao
 * Room dao
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Dao
interface PhraseDao {

    @Query("SELECT * FROM phrase")
    fun getAll(): Flowable<List<EPhrase>>

    @Query("SELECT *FROM phrase WHERE category LIKE :query")
    fun getByCategory(query: String): Flowable<List<EPhrase>>

    @Query("SELECT * FROM phrase WHERE word LIKE :query OR transcription LIKE :query")
    fun findBy(query: String): Maybe<List<EPhrase>>

    @Query("SELECT * FROM phrase WHERE favourite LIKE :query")
    fun getFavourite(query: Boolean): Flowable<List<EPhrase>>

    @Update
    fun updateFavorite(dict: EPhrase)

    @Insert
    fun insertAll(vararg dict: EPhrase)

    @Delete
    fun delete(dict: EPhrase)
}