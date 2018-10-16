package com.leoart.koreanphrasebook.ui.models

import com.google.firebase.database.Exclude
import java.util.*

data class Letter(
        var uid: String,
        var koreanLetter: String,
        var translateLetter: String
) {

    @Exclude
    fun toMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["korean"] = koreanLetter
        map["translation"] = translateLetter
        return map
    }
}