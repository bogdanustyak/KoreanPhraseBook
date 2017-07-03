package com.leoart.koreanphrasebook.ui.favourite

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.Auth
import com.leoart.koreanphrasebook.data.models.User
import com.leoart.koreanphrasebook.data.network.firebase.auth.FRAuth
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import rx.Observable
import rx.Observer

/**
 * Created by bogdan on 6/18/17.
 */
class SignUpFragment(title: String) : BaseFragment(title) {

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmPassword: EditText? = null
    private var mainView: MainView? = null
    private var auth: Auth? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sign_up, container, false)
        initUI(view)
        auth = FRAuth()
        return view
    }

    private fun initUI(view: View) {
        etEmail = view.findViewById(R.id.et_email) as EditText
        etPassword = view.findViewById(R.id.et_password) as EditText
        etConfirmPassword = view.findViewById(R.id.et_confirm_password) as EditText
        view.findViewById(R.id.bt_sign_up).setOnClickListener {
            onSignUpClick()
        }
    }

    private fun onSignUpClick() {
        if (emailIsValid() && passwordIsValid()) {
            auth?.register(email(), password())
                    ?.subscribe (object: Observer<User> {
                        override fun onCompleted() {

                        }

                        override fun onNext(t: User?) {
                            Toast.makeText(context, "SIGNED in!!!", Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(e: Throwable?) {
                            Toast.makeText(context, e?.message?: "Some error occurred", Toast.LENGTH_SHORT).show()
                        }

                    })
        } else {
            Toast.makeText(context, "Data is not valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun emailIsValid(): Boolean {
        val email = email()
        return !TextUtils.isEmpty(email)
    }

    private fun passwordIsValid(): Boolean {
        val pass = password()
        val confirmPass = etConfirmPassword?.text?.toString()
        return !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirmPass) && pass == confirmPass
    }

    private fun email() = etEmail!!.text.toString()
    private fun password() = etPassword!!.text.toString()

    companion object {
        fun newInstance(title: String, mainView: MainView?): SignUpFragment {
            val fragment = SignUpFragment(title)
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}