package com.leoart.koreanphrasebook.ui

import android.support.v4.app.Fragment

/**
 * Created by bogdan on 6/18/17.
 */
abstract class BaseFragment(var title: String) : Fragment() {

    constructor() : this("")

    fun setTitle(){
        arguments?.let{
            this.title = it.getString(MainActivity.TITLE)
        }
        (context as MainView).setTitle(title)
    }
}