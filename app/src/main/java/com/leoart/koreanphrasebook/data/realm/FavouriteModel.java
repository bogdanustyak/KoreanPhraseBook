package com.leoart.koreanphrasebook.data.realm;

import com.leoart.koreanphrasebook.ui.models.Phrase;

import org.jetbrains.annotations.NotNull;

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
    String word;
    String translation;
    String transcription;

    public FavouriteModel() {
        this(String.valueOf(new Random().nextDouble()), "", "", "");
    }

    public FavouriteModel(String phraseKey, String word, String translation, String transcription) {
        this.phraseKey = phraseKey;
        this.word = word;
        this.translation = translation;
        this.transcription = transcription;
    }

    public FavouriteModel(@NotNull Phrase phrase) {
        this(phrase.getKey(), phrase.getWord(), phrase.getTranslation(), phrase.getTranscription());
    }

    @NotNull
    public Phrase toPhase() {
        Phrase phrase = new Phrase(word, translation, transcription, 0, true);
        phrase.setKey(phraseKey);
        return phrase;
    }
}
