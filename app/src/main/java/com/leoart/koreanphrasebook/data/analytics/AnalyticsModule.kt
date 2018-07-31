package com.leoart.koreanphrasebook.data.analytics

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule{

    @Provides
    @Singleton
    fun analyticsManager(application: Application):AnalyticsManager{
        return AnalyticsManagerImpl(application)
    }
}