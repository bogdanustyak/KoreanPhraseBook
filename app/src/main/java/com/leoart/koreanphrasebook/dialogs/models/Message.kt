package com.leoart.koreanphrasebook.dialogs.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by bogdan on 11/14/16.
 */
class Message(val author: Author, val messages: List<String>) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Message> = object : Parcelable.Creator<Message> {
            override fun createFromParcel(source: Parcel): Message = Message(source)
            override fun newArray(size: Int): Array<Message?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<Author>(Author::class.java.classLoader), source.createStringArrayList())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(author, 0)
        dest?.writeStringList(messages)
    }
}