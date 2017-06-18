package com.leoart.koreanphrasebook.ui

/**
 * Created by bogdan on 6/18/17.
 */
interface MainView {
    fun replace(fragment: BaseFragment)
    fun add(fragment: BaseFragment)
    fun add(fragment: BaseFragment, title: String)
}