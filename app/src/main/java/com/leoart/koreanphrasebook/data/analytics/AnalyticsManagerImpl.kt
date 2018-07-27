package com.leoart.koreanphrasebook.data.analytics

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsManagerImpl(private val context: Context) : AnalyticsManager {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    init {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
    }

    override fun onOpenScreen(screenName: String) {
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "screen")
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params)
        Log.d("ASD", screenName)
    }

    override fun openChapter(chapter: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("chapter", chapter))
    }

    override fun openChapterCategory(chapterCategory: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("chapter_category", chapterCategory))
        Log.d("ASD", "DAGGER WORKS FINE")
    }

    override fun openDialog(dialog: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("dialog", dialog))
    }

    override fun onShare() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("info", "share"))
    }

    override fun onWriteNote() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("info", "write_note"))
    }

    override fun onSendEmail() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("info", "send_email"))
    }

    override fun onAbout() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                generateContent("info", "about"))
    }

    override fun addToFavourite(itemType: String, item: String) {
        val params = generateFavouriteParams(itemType, item)
        params.putBoolean(FirebaseAnalytics.Param.CONTENT, true)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
    }

    override fun removeFromFavourite(itemType: String, item: String) {
        val params = generateFavouriteParams(itemType, item)
        params.putBoolean(FirebaseAnalytics.Param.CONTENT, false)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
    }

    private fun generateFavouriteParams(itemType: String, item: String): Bundle {
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemType)
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, item)
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "favourite")
        return params
    }

    private fun generateContent(contentType: String, content: String): Bundle {
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        params.putString(FirebaseAnalytics.Param.CONTENT, content)
        return params
    }
}
