package com.leoart.koreanphrasebook.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.utils.light
import org.jetbrains.anko.*

/**
 * Created by bogdan on 6/18/17.
 */
open class SimpleTextItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                id = R.id.ll_holder
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.HORIZONTAL
                padding = dip(10)
                gravity = Gravity.CENTER_VERTICAL
                textView {
                    id = R.id.tv_name
                    text = "My light text"
                    textSize = 18f
                    typeface = light
                    textColor = R.color.list_text_color
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginStart = dip(20)
                    marginEnd = dip(20)
                }
            }
        }
    }
}