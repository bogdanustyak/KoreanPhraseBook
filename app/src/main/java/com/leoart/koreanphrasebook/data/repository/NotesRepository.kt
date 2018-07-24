package com.leoart.koreanphrasebook.data.repository

import android.content.Context
import com.leoart.koreanphrasebook.data.repository.models.ENote
import com.leoart.koreanphrasebook.ui.models.Note
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*

class NotesRepository(val context: Context) {

    fun fetchNotes(): Flowable<List<Note>> {
        return getDataFromDB()
                .flatMap {
                    return@flatMap Flowable.just(mapNotes(it))
                }
    }

    fun addNote(notes: List<Note>): Completable {
        return Completable.create { emitter ->
            val eNotes = mapENotes(notes).toTypedArray()
            localDB().subscribe {
                it.notesDao().insertAll(*eNotes)
                emitter.onComplete()
            }
        }
    }

    fun updateNote(note: Note): Completable {
        return Completable.create { emitter ->
            val eNote = ENote(note.uid, note.title, note.description)
            localDB().subscribe {
                it.notesDao().updateNote(eNote)
                emitter.onComplete()
            }
        }
    }

    fun deleteNote(note: Note): Completable {
        return Completable.create { emitter ->
            val eNote = ENote(note.uid, note.title, note.description)
            localDB().subscribe {
                it.notesDao().deleteNote(eNote)
                emitter.onComplete()
            }
        }
    }

    private fun getDataFromDB(): Flowable<List<ENote>> {
        return AppDataBase.getInstance(context).notesDao().getAll()
    }

    private fun mapENotes(notes: List<Note>): List<ENote> {
        return notes.map {
            ENote(
                    UUID.randomUUID().toString(),
                    it.title,
                    it.description
            )
        }
    }

    private fun mapNotes(notes: List<ENote>): List<Note> {
        return notes.map {
            Note(
                    it.uid,
                    it.title,
                    it.description
            )
        }
    }

    private fun localDB(): Observable<AppDataBase> {
        return Observable.just(AppDataBase.getInstance(context))
                .subscribeOn(Schedulers.io())
    }
}