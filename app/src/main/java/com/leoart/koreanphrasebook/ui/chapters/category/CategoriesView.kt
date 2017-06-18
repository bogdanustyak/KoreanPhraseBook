package com.leoart.koreanphrasebook.ui.chapters.category

import com.leoart.koreanphrasebook.ui.models.Category

/**
 * Created by bogdan on 11/6/16.
 */
interface CategoriesView {
    fun showCategories(categories: List<Category>)
}