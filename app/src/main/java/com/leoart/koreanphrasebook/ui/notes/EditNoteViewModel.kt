package com.leoart.koreanphrasebook.ui.notes

import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.repository.NotesRepository
import com.leoart.koreanphrasebook.ui.models.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditNoteViewModel(private val repository: NotesRepository) : ViewModel() {

    fun writeNoteToDB(note: Note) {
        repository.addNote(listOf(note))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun updateNoteInDB(note: Note) {
        repository.updateNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

}