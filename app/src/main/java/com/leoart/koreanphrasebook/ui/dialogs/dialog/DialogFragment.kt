package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.Replic
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainActivity
import java.util.*

@SuppressLint("ValidFragment")
/**
 * Created by bogdan on 6/18/17.
 */
class DialogFragment : BaseFragment(), DialogMessagesView {

    var dialog: DialogResponse? = null
    private val adapter = DialogMessagesRecyclerAdapter(ArrayList())
    private var presenter: DialogPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_dialog, container, false)
        presenter = DialogPresenter(this, view.context)
        initUI(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    private fun initUI(view: View) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val rvDialog = view.findViewById<RecyclerView>(R.id.rv_dialog)
        rvDialog.layoutManager = layoutManager
        rvDialog.itemAnimator = DefaultItemAnimator()
        rvDialog.adapter = adapter
        dialog?.let {
            if (!TextUtils.isEmpty(it.uid)) {
                presenter?.getReplics(it.uid)
            }
        }
    }

    override fun showReplics(replics: List<Replic>) {
        adapter.updateReplics(replics)
    }

    companion object {
        fun newInstance(title: String, dialog: DialogResponse): DialogFragment {
            val fragment = DialogFragment()
            val args = Bundle()
            args.putString(MainActivity.TITLE, title)
            fragment.arguments = args
            fragment.dialog = dialog
            return fragment
        }
    }
}