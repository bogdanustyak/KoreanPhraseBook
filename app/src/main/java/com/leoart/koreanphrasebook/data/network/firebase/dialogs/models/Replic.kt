package com.leoart.koreanphrasebook.data.network.firebase.dialogs.models

import com.google.firebase.database.Exclude
import java.util.*

/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */

class Replic(val uid: String = "", val korean: String = "", val ukrainian: String = "",
             val number: Int = 0, val transcription: String = "") {

    constructor(korean: String, ukrainian: String, number: Int) : this("", korean, ukrainian, number)

    constructor(korean: String, ukrainian: String, number: Int, transcription: String)
            : this("", korean, ukrainian, number, transcription)

    override fun toString(): String {
        return "Replica{" +
                "uid='" + uid + '\'' +
                ", korean='" + korean + '\'' +
                ", ukrainian='" + ukrainian + '\'' +
                ", number=" + number +
                '}'
    }

    @Exclude
    fun toMap(): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        // map.put("uid", uid)
        map.put("korean", korean)
        map.put("ukrainian", ukrainian)
        map.put("number", number)
        map.put("transcription", transcription)
        return map
    }
}
