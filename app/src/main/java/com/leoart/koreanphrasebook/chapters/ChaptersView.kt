package com.leoart.koreanphrasebook.chapters

import com.leoart.koreanphrasebook.chapters.models.Chapter

/**
 * Created by bogdan on 11/6/16.
 */
interface ChaptersView {
    fun showChapters(chapters: List<Chapter>?)
}