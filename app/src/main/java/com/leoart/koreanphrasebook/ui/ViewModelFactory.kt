package com.leoart.koreanphrasebook.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.CategoriesRequest
import com.leoart.koreanphrasebook.data.repository.AlphabetRepository
import com.leoart.koreanphrasebook.data.repository.DictionaryRepository

import com.leoart.koreanphrasebook.data.repository.FavouriteRepository
import com.leoart.koreanphrasebook.data.repository.PhraseRepository
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhraseViewModel
import com.leoart.koreanphrasebook.ui.favourite.FavouriteViewModel
import com.leoart.koreanphrasebook.data.repository.NotesRepository
import com.leoart.koreanphrasebook.ui.alphabet.AlphabetViewModel
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesRepository
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesViewModel
import com.leoart.koreanphrasebook.ui.dialogs.dialog.DialogViewModel
import com.leoart.koreanphrasebook.ui.dialogs.dialog.DiealogRepository
import com.leoart.koreanphrasebook.ui.notes.EditNoteViewModel
import com.leoart.koreanphrasebook.ui.notes.NotesViewModel
import com.leoart.koreanphrasebook.ui.splash.SplashViewModel
import com.leoart.koreanphrasebook.ui.splash.SyncDataRepository
import com.leoart.koreanphrasebook.ui.sync.SyncViewModel
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
            modelClass.isAssignableFrom(FavouriteViewModel::class.java) -> {
                FavouriteViewModel(FavouriteRepository(context)) as T
            }
            modelClass.isAssignableFrom(PhraseViewModel::class.java) -> {
                PhraseViewModel(PhraseRepository(context)) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(SyncDataRepository(context)) as T
            }
            modelClass.isAssignableFrom(SyncViewModel::class.java) -> {
                SyncViewModel(SyncDataRepository(context)) as T
            }
            modelClass.isAssignableFrom(CategoriesViewModel::class.java) -> {
                CategoriesViewModel(CategoriesRepository(context)) as T
            }
            modelClass.isAssignableFrom(DialogViewModel::class.java) ->{
                DialogViewModel(DiealogRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}