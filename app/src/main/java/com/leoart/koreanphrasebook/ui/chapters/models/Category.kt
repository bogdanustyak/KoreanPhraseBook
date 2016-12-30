package com.leoart.koreanphrasebook.ui.chapters.models

import com.google.firebase.database.Exclude
import java.util.*

/**
 * Created by bogdan on 11/5/16.
 */
class Category(val id: String, val name: HashMap<String, String>) {

    @Exclude
    fun toMap(): HashMap<String, Any> {
        val map = HashMap<String, Any>()
        // map.putAll(name.toMap())
        // map.put("id", translation)
        //map.put("chapter", chapter)
        // map.put("index", index)
        return map
    }
}
