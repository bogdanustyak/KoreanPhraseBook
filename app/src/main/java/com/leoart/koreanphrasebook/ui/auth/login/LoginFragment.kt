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

    override fun initToolbar() {}

    private var mainView: MainView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        (context as MainView).setTitle(getString(R.string.bt_login))
    }

    private fun onLoginClick() {

    }

    companion object {

        fun newInstance(mainView: MainView?): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}