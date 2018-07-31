package com.leoart.koreanphrasebook.data.analytics

import com.leoart.koreanphrasebook.di.AppModule
import com.leoart.koreanphrasebook.di.ActivityBindingModule
import com.leoart.koreanphrasebook.KoreanPhrasebookApp
import com.leoart.koreanphrasebook.di.FragmentBindingModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AnalyticsModule::class,
    ActivityBindingModule::class,
    FragmentBindingModule::class
])
interface ApplicationComponent {
    fun inject(application: KoreanPhrasebookApp)
}