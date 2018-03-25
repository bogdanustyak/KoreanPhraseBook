package com.leoart.koreanphrasebook.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * SoftKeyboard
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class SoftKeyboard(private val activity: Activity) {

    fun hide() {
        if (activity.currentFocus != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    fun show() {
        if (activity.currentFocus != null) {
            val inputMgr = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMgr.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)
        }
    }
}