package com.leoart.koreanphrasebook;

import com.leoart.koreanphrasebook.ui.models.Chapter;
import com.leoart.koreanphrasebook.ui.models.Phrase;
import com.leoart.koreanphrasebook.ui.dialogs.models.Author;
import com.leoart.koreanphrasebook.ui.dialogs.models.Dialog;
import com.leoart.koreanphrasebook.ui.dialogs.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class DemoDataProvider {

    public List<Dialog> getDialogs() {
        List<Dialog> dialogs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dialogs.add(new Dialog("Dialog " + i, messages()));
        }
        return dialogs;
    }

    public List<Message> messages() {
        List<Message> messages = new ArrayList<>();
        List<String> text = new ArrayList<>();
        text.add("this is test string");
        text.add("Second one");
        text.add("Third one");
        for (int i = 0; i < 10; i++) {
            messages.add(new Message(new Author("Bogdan" + i), text));
        }
        return messages;
    }

    public List<Chapter> getChapters() {
//        List<Chapter> chapters = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            chapters.add(new Chapter("Основні вирази " + i, getChapterCategories()));
//        }
        //return chapters;
        return null;
    }

    public List<Phrase> getPhrases() {
        List<Phrase> phrases = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            phrases.add(new Phrase("Hello", "Ola", "ola", i));
        }
        return phrases;
    }
}
