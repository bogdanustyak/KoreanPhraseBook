package com.leoart.koreanphrasebook.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by bogdan on 11/5/16.
 */
public class Chapter implements Parcelable {

    private String key;
    private String name;
    private String icon;
    private HashMap<String, Boolean> categories;

    public Chapter() {

    }

    public Chapter(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Chapter(String name, HashMap<String, Boolean> categories) {
        this.name = name;
        this.categories = categories;
    }

    public Chapter(String key, String name, String icon) {
        this.key = key;
        this.name = name;
        this.icon = icon;
    }

    public Chapter(String key, String name, String icon, HashMap<String, Boolean> categories) {
        this.key = key;
        this.name = name;
        this.icon = icon;
        this.categories = categories;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Boolean> getCategories() {
        return categories;
    }

    public void setCategories(HashMap<String, Boolean> categories) {
        this.categories = categories;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeSerializable(this.categories);
    }

    protected Chapter(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
        this.categories = (HashMap<String, Boolean>) in.readSerializable();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };
}
