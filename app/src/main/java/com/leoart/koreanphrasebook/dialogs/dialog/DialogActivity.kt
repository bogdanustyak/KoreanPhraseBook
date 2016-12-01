package com.leoart.koreanphrasebook.dialogs.dialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.dialogs.models.Dialog

class DialogActivity : AppCompatActivity(), DialogMessagesView {

    companion object {
        val DIALOG = "dialog"
    }

    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

       setupToolbar()

        val layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        val rvDialog = findViewById(R.id.rv_dialog) as RecyclerView
        rvDialog.layoutManager = layoutManager
        rvDialog.itemAnimator = DefaultItemAnimator()

        dialog = intent.getParcelableExtra(DIALOG)
        rvDialog.adapter = DialogMessagesRecyclerAdapter(dialog?.messages)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.home ->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        title = getString(R.string.phrases)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
