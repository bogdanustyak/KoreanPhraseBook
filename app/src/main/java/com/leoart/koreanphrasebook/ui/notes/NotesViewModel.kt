package com.leoart.koreanphrasebook.ui.notes

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.repository.NotesRepository
import com.leoart.koreanphrasebook.ui.models.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotesViewModel(val repository: NotesRepository) : ViewModel() {

    private val notes: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    fun notesData(): LiveData<List<Note>> {
        if (this.notes.value == null) {
            this.notes.value = mutableListOf()
        }
        return this.notes
    }

    fun fetchNotes() {
        repository.fetchNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ notes ->
                    this.notes.value = notes
                }, {
                    throw UnsupportedOperationException("not implemented")
                })
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

}