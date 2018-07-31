package com.leoart.koreanphrasebook.data.analytics

interface AnalyticsManager {

    fun onOpenScreen(screenName: String)

    fun addToFavourite(itemType: String, item: String)

    fun removeFromFavourite(itemType: String, item: String)

    fun openChapter(chapter: String)

    fun openChapterCategory(chapterCategory: String)

    fun openDialog(dialog: String)

    fun onShare()

    fun onWriteNote()

    fun onSendEmail()

    fun onAbout()

}