package com.leoart.koreanphrasebook.dialogs

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.chapters.models.DialogsModel
import com.leoart.koreanphrasebook.dialogs.dialog.DialogActivity
import com.leoart.koreanphrasebook.dialogs.models.Dialog

class DialogsFragment : Fragment(), DialogsView, DialogsRecyclerAdapter.DialogsListInteractionListener {

    private var adapter: DialogsRecyclerAdapter? = null;
    var rvDialogs: RecyclerView? = null

    override fun showDialogs(dialogs: List<DialogsModel>?) {
        adapter?.setDialogs(dialogs)

    }

    private var dialogs: List<DialogsModel>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dialogs, container, false)

        rvDialogs = view.findViewById(R.id.rv_dialogs) as RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvDialogs?.layoutManager = layoutManager
        rvDialogs?.itemAnimator = DefaultItemAnimator()

        adapter = DialogsRecyclerAdapter(dialogs, this)
        rvDialogs?.adapter = adapter

        DialogsPresenter(this)
                .requestDialogs()

        return view
    }

    override fun onDialogClick(dialog: DialogsModel) {
//        val intent = Intent(activity, DialogActivity::class.java)
//        intent.putExtra(DialogActivity.DIALOG, dialog)
//        startActivity(intent)
    }

    companion object {

        fun newInstance(): DialogsFragment {
            val fragment = DialogsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
