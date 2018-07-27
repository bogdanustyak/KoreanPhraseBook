package com.leoart.koreanphrasebook.data.analytics

import com.leoart.koreanphrasebook.AppModule
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesFragment
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.search.SearchActivity
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AnalyticsModule::class])
interface AnalyticsComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: VocabularyFragment)
    fun inject(fragment: CategoriesFragment)
    fun inject(fragment: ChapterFragment)
    fun inject(fragment: InfoFragment)
    fun inject(activity: SearchActivity)
}