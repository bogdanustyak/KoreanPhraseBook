package com.leoart.koreanphrasebook.data.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.leoart.koreanphrasebook.data.repository.dao.DialogDao
import com.leoart.koreanphrasebook.data.repository.dao.ReplicDao
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import com.leoart.koreanphrasebook.data.repository.models.EReplic

/**
 * AppDataBase
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
@Database(entities = [
    EDialog::class,
    EReplic::class], version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun dialogDao(): DialogDao
    abstract fun replicsDao(): ReplicDao

    companion object {
        private val DATA_BASE_NAME = "KoreanPhraseBook.db"
        @Volatile private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDataBase::class.java, DATA_BASE_NAME)
                        .build()
    }
}
