package com.leoart.koreanphrasebook.di

import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun searchActivity(): SearchActivity
}
