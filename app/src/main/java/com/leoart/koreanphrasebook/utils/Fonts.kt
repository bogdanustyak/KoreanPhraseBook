package com.leoart.koreanphrasebook.utils

import android.graphics.Typeface
import android.widget.TextView

/**
 * Created by bogdan on 6/18/17.
 */

val TextView.light: Typeface
    get() =
        Typeface.createFromAsset(context.assets, "fonts/Lato-Light.ttf")
