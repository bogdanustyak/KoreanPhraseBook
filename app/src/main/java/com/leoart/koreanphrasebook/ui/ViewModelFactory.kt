package com.leoart.koreanphrasebook.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.leoart.koreanphrasebook.data.repository.DictionaryRepository
import com.leoart.koreanphrasebook.ui.vocabulary.DictionaryViewModel

/**
 * Factory for ViewModels
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            return DictionaryViewModel(DictionaryRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}