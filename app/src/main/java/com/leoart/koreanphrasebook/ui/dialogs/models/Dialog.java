package com.leoart.koreanphrasebook.ui.dialogs.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bogdan on 11/5/16.
 */
public class Dialog implements Parcelable {

    private final String name;
    private final List<Message> messages;

    public Dialog() {
        this("", new ArrayList<Message>());
    }

    public Dialog(String name) {
        this(name, new ArrayList<Message>());
    }

    public Dialog(List<Message> messages) {
        this("", messages);
    }

    public Dialog(String name, List<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    public void addMessage(Message message) throws IllegalArgumentException {
        if (message == null) {
            throw new IllegalArgumentException("message should not be empty");
        }
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.messages);
    }

    protected Dialog(Parcel in) {
        this.name = in.readString();
        this.messages = new ArrayList<Message>();
        in.readList(this.messages, Message.class.getClassLoader());
    }

    public static final Creator<Dialog> CREATOR = new Creator<Dialog>() {
        @Override
        public Dialog createFromParcel(Parcel source) {
            return new Dialog(source);
        }

        @Override
        public Dialog[] newArray(int size) {
            return new Dialog[size];
        }
    };
}
