package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single


/**
 * Dialog dao
 * Room dao
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Dao
interface DialogDao {

    @Query("SELECT * FROM dialog")
    fun getAll(): Flowable<List<EDialog>>

    @Query("SELECT * FROM dialog WHERE dialogTitle LIKE :dialogName")
    fun findByName(dialogName: String): Maybe<List<EDialog>>

    @Insert
    fun insertAll(vararg dialogs: EDialog)

    @Delete
    fun delete(dialog: EDialog)

    @Query("SELECT count(*) FROM dialog")
    fun count() : Single<Int>
}