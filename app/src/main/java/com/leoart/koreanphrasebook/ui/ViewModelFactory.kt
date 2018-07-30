package com.leoart.koreanphrasebook.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.leoart.koreanphrasebook.data.repository.AlphabetRepository
import com.leoart.koreanphrasebook.data.repository.DictionaryRepository
import com.leoart.koreanphrasebook.data.repository.NotesRepository
import com.leoart.koreanphrasebook.ui.alphabet.AlphabetViewModel
import com.leoart.koreanphrasebook.ui.notes.EditNoteViewModel
import com.leoart.koreanphrasebook.ui.notes.NotesViewModel
import com.leoart.koreanphrasebook.ui.vocabulary.DictionaryViewModel

/**
 * Factory for ViewModels
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DictionaryViewModel::class.java) -> {
                DictionaryViewModel(DictionaryRepository(context)) as T
            }
            modelClass.isAssignableFrom(NotesViewModel::class.java) -> {
                NotesViewModel(NotesRepository(context)) as T
            }
            modelClass.isAssignableFrom(EditNoteViewModel::class.java) -> {
                EditNoteViewModel(NotesRepository(context)) as T
            }
            modelClass.isAssignableFrom(AlphabetViewModel::class.java) -> {
                AlphabetViewModel(AlphabetRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}