package com.leoart.koreanphrasebook.ui.sync

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.*
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseActivity
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.utils.resource.Resource
import com.leoart.koreanphrasebook.utils.resource.Status
import kotlinx.android.synthetic.main.data_refresh_fragment.*


class SyncActivity : BaseActivity() {

    private lateinit var model: SyncViewModel
    private var progressView: ConstraintLayout? = null
    private var dismissButton: TextView? = null
    private var syncButton: Button? = null

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
        progressView = findViewById(R.id.progressLayout)
        dismissButton = findViewById(R.id.dismissButton)
        dismissButton?.setOnClickListener {
            openMainScreen()
        }

        syncButton = findViewById(R.id.syncButton)
        syncButton?.setOnClickListener {
            showLoading()
            dismissButton?.isClickable = false
            syncButton?.isClickable = false
            model.getSyncInfo().observe(this, Observer<Resource<Boolean>> { data ->
                when (data?.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        openMainScreen()
                    }
                    Status.ERROR -> {
                        openMainScreen()
                        hideLoading()
                        Toast.makeText(this, R.string.request_error_message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                    }
                }
            })
        }
    }

    private fun showLoading() {
        progressView?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressView?.visibility = View.INVISIBLE
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}