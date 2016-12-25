package com.leoart.koreanphrasebook.ui.chapters.models;

/**
 * Created by bogdan on 11/5/16.
 */
public class Category {
    private String id;
    private String name;
    private String chapter;

    public Category() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getCategory() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
}
