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
class AuthFragment : BaseFragment() {

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

    override fun onResume() {
        super.onResume()
        (context as MainView).setTitle(getString(R.string.auth))
    }

    private fun onLoginClick() {
        this.mainView?.replace(
                LoginFragment.newInstance(mainView)
        )
    }

    private fun onSignUpClick() {
        this.mainView?.replace(
                SignUpFragment.newInstance(mainView)
        )
    }

    companion object {

        fun newInstance(mainView: MainView?): AuthFragment {
            val fragment = AuthFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}