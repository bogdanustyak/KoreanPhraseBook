package com.leoart.koreanphrasebook;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;

import com.leoart.koreanphrasebook.data.analytics.ApplicationComponent;
import com.leoart.koreanphrasebook.data.analytics.AnalyticsModule;
import com.leoart.koreanphrasebook.data.analytics.DaggerApplicationComponent;
import com.leoart.koreanphrasebook.di.AppModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bogdan on 6/18/17.
 */

public class KoreanPhrasebookApp extends MultiDexApplication implements HasActivityInjector {

    private ApplicationComponent applicationComponent;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

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

}
