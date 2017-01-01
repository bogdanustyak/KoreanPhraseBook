package com.leoart.koreanphrasebook.data.parsers.categories

import android.content.Context
import com.leoart.koreanphrasebook.data.network.firebase.CategoriesRequest

/**
 * Created by khrystyna on 12/30/16.
 */

class CategoryParser(private val context: Context) {

    fun writeToFirebaseDB() {
        val dict = CategoryStream(context.assets.open("categories.txt")).parse()
        CategoriesRequest().writeCategories("chapter6", dict)
    }
}
