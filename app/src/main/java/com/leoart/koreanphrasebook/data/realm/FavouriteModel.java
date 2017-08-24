package com.leoart.koreanphrasebook.data.realm;

import java.util.Random;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by khrystyna on 8/24/17.
 */

public class FavouriteModel extends RealmObject {

    @PrimaryKey
    @Required
    String phraseKey;

    public FavouriteModel(String phraseKey) {
        this.phraseKey = phraseKey;
    }

    public FavouriteModel() {
        this(String.valueOf(new Random().nextDouble()));
    }
}
