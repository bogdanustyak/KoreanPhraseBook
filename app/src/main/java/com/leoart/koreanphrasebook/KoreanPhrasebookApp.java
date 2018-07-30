package com.leoart.koreanphrasebook;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.Fragment;

import com.leoart.koreanphrasebook.data.analytics.AnalyticsComponent;
import com.leoart.koreanphrasebook.data.analytics.AnalyticsModule;
import com.leoart.koreanphrasebook.data.analytics.DaggerAnalyticsComponent;
import com.leoart.koreanphrasebook.di.AppModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bogdan on 6/18/17.
 */

public class KoreanPhrasebookApp extends MultiDexApplication implements HasActivityInjector, HasSupportFragmentInjector {

    private AnalyticsComponent analyticsComponent;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        analyticsComponent = DaggerAnalyticsComponent
                .builder()
                .appModule(new AppModule(this))
                .analyticsModule(new AnalyticsModule())
                .build();
        analyticsComponent.inject(this);
    }

    public AnalyticsComponent getAnalyticsComponent() {
        return analyticsComponent;
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
