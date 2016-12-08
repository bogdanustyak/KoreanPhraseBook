package com.leoart.koreanphrasebook.data.network.firebase.dialogs.models

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
data class DialogResponse(val uid: String, val name: String) : Parcelable{
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DialogResponse> = object : Parcelable.Creator<DialogResponse> {
            override fun createFromParcel(source: Parcel): DialogResponse = DialogResponse(source)
            override fun newArray(size: Int): Array<DialogResponse?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(uid)
        dest?.writeString(name)
    }
}