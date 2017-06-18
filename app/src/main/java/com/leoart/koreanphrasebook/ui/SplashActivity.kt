package com.leoart.koreanphrasebook.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.badoo.mobile.util.WeakHandler
import com.leoart.koreanphrasebook.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 1500
    private val handler = WeakHandler()

    private val splashTask = {
        openMainScreen()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(splashTask, SPLASH_DELAY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(splashTask)
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
