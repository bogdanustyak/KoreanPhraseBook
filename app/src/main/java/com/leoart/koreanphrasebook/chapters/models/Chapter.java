package com.leoart.koreanphrasebook.chapters.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by bogdan on 11/5/16.
 */
public class Chapter implements Parcelable {

    private String name;
    private HashMap<String, Boolean> categories;

    public Chapter() {

    }

    public Chapter(String name, HashMap<String, Boolean> categories) {
        this.name = name;
        this.categories = categories;
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
        dest.writeString(this.name);
        dest.writeSerializable(this.categories);
    }

    protected Chapter(Parcel in) {
        this.name = in.readString();
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
