package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import com.leoart.koreanphrasebook.data.repository.models.ENote
import com.leoart.koreanphrasebook.ui.models.Note
import io.reactivex.Flowable

class NotesRepository(val context: Context) {

    fun fetchNotes() : Flowable<List<Note>>{
        return getDataFromDB()
                .flatMap {
                    return@flatMap Flowable.just(mapNotes(it))
                }
    }

    fun addNote(note : Note){
        writeDataToDB(mapFromNote(note))
    }

    private fun writeDataToDB(note: ENote){
        AppDataBase.getInstance(context).notesDao().insertAll(note)
    }

    private fun getDataFromDB(): Flowable<List<ENote>> {
        return AppDataBase.getInstance(context).notesDao().getAll()
    }

    private fun mapNotes(notes : List<ENote>) : List<Note>{
        return notes.map {
            Note(
                it.title,
                it.description
            )
        }
    }

    private fun mapFromNote(note : Note) : ENote{
        return ENote(note.hashCode().toString(), note.title, note.description)
    }
}