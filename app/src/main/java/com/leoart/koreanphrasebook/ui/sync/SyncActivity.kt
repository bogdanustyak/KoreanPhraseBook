package com.leoart.koreanphrasebook.ui.sync

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseActivity
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.data_refresh_fragment.*

class SyncActivity : BaseActivity() {

    private lateinit var model: SyncViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_refresh_fragment)
        model = ViewModelProviders.of(
                this,
                ViewModelFactory(applicationContext)
        ).get(SyncViewModel::class.java)
        initUI()
    }

    private fun initUI() {
        dismissButton.setOnClickListener {
            openMainScreen()
        }
        syncButton.setOnClickListener {
            showLoading()
            dismissButton.isClickable = false
            syncButton.isClickable = false
            model.refreshData().observeOn(Schedulers.io())
                    .subscribe({
                        hideLoading()
                        openMainScreen()
                    }, {
                        hideLoading()
                        openMainScreen()
                        it.printStackTrace()
                    })
        }
    }

    private fun showLoading() {
        findViewById<FrameLayout>(R.id.progressLayout).visibility = View.VISIBLE
    }

    private fun hideLoading() {
        findViewById<FrameLayout>(R.id.progressLayout).visibility = View.INVISIBLE
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}