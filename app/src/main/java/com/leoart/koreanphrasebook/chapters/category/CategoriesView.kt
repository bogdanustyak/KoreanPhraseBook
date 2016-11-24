package com.leoart.koreanphrasebook.chapters.category

import com.leoart.koreanphrasebook.chapters.models.Category

/**
 * Created by bogdan on 11/6/16.
 */
interface CategoriesView {
    fun showCategories(categories: List<Category>)
}