package com.leoart.koreanphrasebook;

import android.support.multidex.MultiDexApplication;

import com.leoart.koreanphrasebook.data.analytics.AnalyticsComponent;
import com.leoart.koreanphrasebook.data.analytics.AnalyticsModule;
import com.leoart.koreanphrasebook.data.analytics.DaggerAnalyticsComponent;

import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bogdan on 6/18/17.
 */

public class KoreanPhrasebookApp extends MultiDexApplication {

    private AnalyticsComponent analyticsComponent;

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
    }

    public AnalyticsComponent getAnalyticsComponent() {
        return analyticsComponent;
    }
}
