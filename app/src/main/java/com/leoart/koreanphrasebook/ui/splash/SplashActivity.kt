package com.leoart.koreanphrasebook.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.badoo.mobile.util.WeakHandler
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.sync.SyncActivity
import com.leoart.koreanphrasebook.utils.NetworkChecker
import com.leoart.koreanphrasebook.utils.resource.Resource
import com.leoart.koreanphrasebook.utils.resource.Status

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 1500
    private var handler: WeakHandler? = null
    private var runnable: Runnable? = null
    private lateinit var model: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        model = ViewModelProviders.of(
                this,
                ViewModelFactory(applicationContext)
        ).get(SplashViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        model.getSyncInfo().observe(this, Observer<Resource<Boolean>> {
            it?.let {
                when (it.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        it.data?.let {
                            onSuccessDataRefresh(it)
                        }
                    }
                    Status.ERROR -> {
                        onError()
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        runnable?.let {
            handler?.removeCallbacks(it)
        }
    }

    private fun onSuccessDataRefresh(shouldOpenMain: Boolean) {
        handler = WeakHandler()
        runnable = Runnable {
            if (shouldOpenMain || !NetworkChecker(this).isNetworkAvailable) {
                openMainScreen()
            } else {
                openSyncScreen()
            }
        }
        handler?.postDelayed(runnable, SPLASH_DELAY)
    }

    private fun onError() {
        handler = WeakHandler()
        runnable = Runnable {
            openMainScreen()
        }
        handler?.postDelayed(runnable, SPLASH_DELAY)
    }

    private fun openSyncScreen() {
        val intent = Intent(this, SyncActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
