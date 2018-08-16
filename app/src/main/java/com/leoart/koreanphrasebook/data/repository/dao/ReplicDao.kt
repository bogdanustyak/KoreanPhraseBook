package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import io.reactivex.Flowable
import io.reactivex.Maybe


/**
 * Replic dao
 * Room dao
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Dao
interface ReplicDao {

    @Query("SELECT * FROM replic")
    fun getAll(): List<EReplic>

    @Query("SELECT * FROM replic WHERE uid LIKE :dialog")
    fun getByUid(dialog: String): Flowable<List<EReplic>>

    @Query("SELECT * FROM replic WHERE ukrainian LIKE :replicTitle OR korean LIKE :replicTitle")
    fun findByReplic(replicTitle: String): Maybe<List<EReplic>>

    @Insert
    fun insertAll(vararg replic: EReplic)

    @Delete
    fun delete(replic: EReplic)
}