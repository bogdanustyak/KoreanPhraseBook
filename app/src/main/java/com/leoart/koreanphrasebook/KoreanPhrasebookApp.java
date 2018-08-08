package com.leoart.koreanphrasebook;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;

import com.leoart.koreanphrasebook.data.analytics.AnalyticsModule;
import com.leoart.koreanphrasebook.data.analytics.ApplicationComponent;
import com.leoart.koreanphrasebook.data.analytics.DaggerApplicationComponent;
import com.leoart.koreanphrasebook.di.AppModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by bogdan on 6/18/17.
 */

public class KoreanPhrasebookApp extends MultiDexApplication implements HasActivityInjector, HasSupportFragmentInjector {

    private ApplicationComponent applicationComponent;
    @Inject

    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = DaggerApplicationComponent
                .builder()
                .appModule(new AppModule(this))
                .analyticsModule(new AnalyticsModule())
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
