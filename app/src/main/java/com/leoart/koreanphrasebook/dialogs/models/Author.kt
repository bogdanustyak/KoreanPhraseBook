package com.leoart.koreanphrasebook.dialogs.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by bogdan on 11/14/16.
 */
class Author(val name: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Author> = object : Parcelable.Creator<Author> {
            override fun createFromParcel(source: Parcel): Author = Author(source)
            override fun newArray(size: Int): Array<Author?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
    }
}