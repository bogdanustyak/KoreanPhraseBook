package com.leoart.koreanphrasebook.ui.chapters.models;

/**
 * Created by bogdan on 11/5/16.
 */
public class Phrase {
    private String word;
    private String translation;
    private String transcription;

    public Phrase(String word, String translation, String transcription) {
        this.word = word;
        this.translation = translation;
        this.transcription = transcription;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getTranscription() {
        return transcription;
    }
}
