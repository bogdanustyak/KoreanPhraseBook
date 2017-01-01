package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import rx.Subscriber
import java.util.*

class DialogActivity : AppCompatActivity(), DialogMessagesView {

    companion object {
        val DIALOG = "dialog"
    }

    var dialog: DialogResponse? = null

    private val adapter = DialogMessagesRecyclerAdapter(ArrayList<Replic>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        setupToolbar()

        val layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        val rvDialog = findViewById(R.id.rv_dialog) as RecyclerView
        rvDialog.layoutManager = layoutManager
        rvDialog.itemAnimator = DefaultItemAnimator()

        dialog = intent.getParcelableExtra(DIALOG)
        rvDialog.adapter = adapter

        if (dialog != null)
            if (!TextUtils.isEmpty(dialog!!.uid))
                DialogsRequest().getAllDialogReplics(dialog!!.uid)
                        .subscribe(object : Subscriber<List<Replic>>() {
                            override fun onError(e: Throwable?) {
                                e?.printStackTrace()
                                throw UnsupportedOperationException("not implemented")
                            }

                            override fun onCompleted() {

                            }

                            override fun onNext(t: List<Replic>?) {
                                adapter.updateReplics(t)
                            }

                        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupToolbar() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        title = getString(R.string.dialogs)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
