package com.leoart.koreanphrasebook.ui.splash

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.badoo.mobile.util.WeakHandler
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.sync.SyncActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
        model.openNextScreen()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    onSuccessDataRefresh(it)
                }, {
                    onError()
                    it.printStackTrace()
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
            if (shouldOpenMain) {
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
            openSyncScreen()
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
