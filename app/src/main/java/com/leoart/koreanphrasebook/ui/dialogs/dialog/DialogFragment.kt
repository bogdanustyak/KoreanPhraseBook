package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.DialogsRequest
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.ui.BaseFragment
import rx.Subscriber
import java.util.*

/**
 * Created by bogdan on 6/18/17.
 */
class DialogFragment(title: String) : BaseFragment(title) {

    var dialog: DialogResponse? = null
    private val adapter = DialogMessagesRecyclerAdapter(ArrayList<Replic>())

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_dialog, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val rvDialog = view.findViewById<RecyclerView>(R.id.rv_dialog)
        rvDialog.layoutManager = layoutManager
        rvDialog.itemAnimator = DefaultItemAnimator()
        rvDialog.adapter = adapter
        dialog?.let {
            if (!TextUtils.isEmpty(it.uid))
                DialogsRequest().getAllDialogReplics(it.uid)
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
        return view
    }

    companion object {
        fun newInstance(title: String, dialog: DialogResponse): DialogFragment {
            val fragment = DialogFragment(title)
            val args = Bundle()
            fragment.arguments = args
            fragment.dialog = dialog
            return fragment
        }
    }
}