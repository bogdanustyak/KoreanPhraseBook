package com.leoart.koreanphrasebook.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView

/**
 * Created by bogdan on 6/18/17.
 */
class LoginFragment : BaseFragment() {

    private var mainView: MainView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    private fun onLoginClick() {

    }

    companion object {

        fun newInstance(title: String, mainView: MainView?): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            fragment.title = title
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}