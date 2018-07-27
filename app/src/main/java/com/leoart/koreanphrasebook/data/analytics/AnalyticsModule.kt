package com.leoart.koreanphrasebook.data.analytics

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule{

    @Provides
    @Singleton
    fun analyticsManager(context: Context):AnalyticsManager{
        return AnalyticsManagerImpl(context.applicationContext)
    }
}