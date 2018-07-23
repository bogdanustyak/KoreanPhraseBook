package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import android.util.Log
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import com.leoart.koreanphrasebook.data.repository.models.ENote
import com.leoart.koreanphrasebook.ui.models.Note
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NotesRepository(val context: Context) {

    fun fetchNotes() : Flowable<List<Note>>{
        return getDataFromDB()
                .flatMap {
                    return@flatMap Flowable.just(mapNotes(it))
                }
    }

//    fun addNote(note : Note) : Observable<List<ENote>>{
////        return Observable.defer { emitter ->
////
////        }
//    }

    private fun writeDataToDB(note: List<ENote>){

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

    private fun mapFromNote(vararg note : Note) : List<ENote>{
        return note.map {
            ENote(it.hashCode().toString(), it.title, it.description)
        }
    }

    fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}