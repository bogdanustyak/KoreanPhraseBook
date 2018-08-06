package com.leoart.koreanphrasebook.data.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import io.reactivex.Flowable
import io.reactivex.Maybe


/**
 * Chapter dao
 * Room dao
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Dao
interface ChapterDao {

    @Query("SELECT * FROM chapter")
    fun getAll(): Flowable<List<EChapter>>

    @Query("SELECT * FROM chapter WHERE name LIKE :chapterName")
    fun findByName(chapterName: String): Maybe<List<EChapter>>

    @Insert
    fun insertAll(vararg chapter: EChapter)

    @Delete
    fun delete(chapter: EChapter)

    @Query("SELECT count(*) FROM chapter")
    fun count() : Flowable<Int>
}