package com.leoart.koreanphrasebook.di

import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesFragment
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun vocabularyFragment(): VocabularyFragment

    @ContributesAndroidInjector
    abstract fun infoFragment(): InfoFragment

    @ContributesAndroidInjector
    abstract fun chapterFragment(): ChapterFragment

    @ContributesAndroidInjector
    abstract fun categoriesFragment(): CategoriesFragment

    @ContributesAndroidInjector
    abstract fun dialogsFragment(): DialogsFragment

}
