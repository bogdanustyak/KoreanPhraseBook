package com.leoart.koreanphrasebook.ui.favourite

import android.os.Bundle
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView

/**
 * Created by bogdan on 6/18/17.
 */
class FavouriteFragment(title: String) : BaseFragment(title) {

    private var mainView: MainView? = null

    companion object {

        fun newInstance(title: String, mainView: MainView?): FavouriteFragment {
            val fragment = FavouriteFragment(title)
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}