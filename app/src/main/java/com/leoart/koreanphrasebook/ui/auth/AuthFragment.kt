package com.leoart.koreanphrasebook.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView

/**
 * Created by bogdan on 6/18/17.
 */
class AuthFragment(title: String) : BaseFragment(title) {

    private var mainView: MainView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        view.findViewById<Button>(R.id.bt_login).setOnClickListener {
            onLoginClick()
        }
        view.findViewById<Button>(R.id.bt_sign_up).setOnClickListener {
            onSignUpClick()
        }
        return view
    }

    private fun onLoginClick() {
        this.mainView?.add(
                LoginFragment.newInstance(getString(R.string.bt_login), mainView)
        )
    }

    private fun onSignUpClick() {
        this.mainView?.add(
                SignUpFragment.newInstance(getString(R.string.bt_register), mainView)
        )
    }

    companion object {

        fun newInstance(title: String, mainView: MainView?): AuthFragment {
            val fragment = AuthFragment(title)
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}