package com.leoart.koreanphrasebook.ui.chapters

import com.leoart.koreanphrasebook.ui.chapters.models.Chapter

/**
 * Created by bogdan on 11/6/16.
 */
interface ChaptersView {
    fun showChapters(chapters: List<Chapter>?)
}