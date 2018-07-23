package com.leoart.koreanphrasebook.ui.notes

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.leoart.koreanphrasebook.data.repository.NotesRepository
import com.leoart.koreanphrasebook.ui.models.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NotesViewModel(val repository : NotesRepository) : ViewModel() {

    private val notes : MutableLiveData<MutableList<Note>> by lazy {
        MutableLiveData<MutableList<Note>>()
    }

    fun notesData() : LiveData<MutableList<Note>> {
        return this.notes
    }

    fun fetchNotes(){
        repository.fetchNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ notes ->
                    this.notes.value?.addAll(notes)
                }, { throwable ->
                    throw UnsupportedOperationException("not implemented")
                })
    }

    fun addNote(note : Note){
        repository.addNote(note)
    }

}