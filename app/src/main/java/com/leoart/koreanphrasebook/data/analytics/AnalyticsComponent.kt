package com.leoart.koreanphrasebook.data.analytics

import com.leoart.koreanphrasebook.di.AppModule
import com.leoart.koreanphrasebook.di.ActivityBindingModule
import com.leoart.koreanphrasebook.KoreanPhrasebookApp
import com.leoart.koreanphrasebook.di.FragmnetBindingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AnalyticsModule::class
    , ActivityBindingModule::class, FragmnetBindingModule::class
]
)
interface AnalyticsComponent {
    //    fun inject(activity: MainActivity)
//    fun inject(fragment: VocabularyFragment)
//    fun inject(fragment: CategoriesFragment)
//    fun inject(fragment: ChapterFragment)
//    fun inject(fragment: InfoFragment)
    fun inject(application: KoreanPhrasebookApp)
}