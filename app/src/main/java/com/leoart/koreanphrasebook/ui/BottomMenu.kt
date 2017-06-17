package com.leoart.koreanphrasebook.ui

import android.widget.ImageView

/**
 * Created by bogdan on 6/14/17.
 */
class BottomMenu(val ivDict: ImageView,
                 val ivFavorite: ImageView,
                 val ivChapters: ImageView,
                 val ivDialogs: ImageView,
                 val ivInfo: ImageView,
                 val listener: BottomMenuListener) {


    init {
        this.ivDict.setOnClickListener {
            this.listener.dictSelected()
        }
        this.ivFavorite.setOnClickListener {
            this.listener.favouriteSelected()
        }
        this.ivChapters.setOnClickListener {
            this.listener.chaptersSelected()
        }
        this.ivDialogs.setOnClickListener {
            this.listener.dialogsSelected()
        }
        this.ivInfo.setOnClickListener {
            this.listener.infoSelected()
        }
    }


    interface BottomMenuListener {

        fun dictSelected()

        fun favouriteSelected()

        fun chaptersSelected()

        fun dialogsSelected()

        fun infoSelected()
    }
}