package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.ELetter
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category")
    fun fetchAll(): Flowable<List<ECategory>>

    @Insert
    fun insertAll(vararg category: ECategory)

    @Query("SELECT * FROM category WHERE ikey LIKE :query")
    fun findBy(query: String): Flowable<List<ECategory>>

    @Query("SELECT count(*) FROM category")
    fun count(): Single<Int>

    @Query("DELETE FROM category")
    fun deleteAll()
}