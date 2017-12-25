package com.leoart.koreanphrasebook.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R

class NoNetworkFragment : BaseFragment("Error") {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.no_internet, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance() = NoNetworkFragment()
    }
}
