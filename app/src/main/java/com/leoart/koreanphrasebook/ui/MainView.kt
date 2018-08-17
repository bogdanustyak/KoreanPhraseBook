package com.leoart.koreanphrasebook.ui

/**
 * Created by bogdan on 6/18/17.
 */
interface MainView {
    fun replace(fragment: BaseFragment, addToBackStack: Boolean = true)
    fun setTitle(title: String)
    fun isNetworkAvailable(): Boolean
    fun showBackArrow()
    fun hideBackArrow()
}