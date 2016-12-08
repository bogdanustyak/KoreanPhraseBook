package com.leoart.koreanphrasebook.ui.dialogs

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
import com.leoart.koreanphrasebook.ui.chapters.models.DialogsModel
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.ui.dialogs.dialog.DialogActivity

class DialogsFragment : Fragment(), DialogsView, DialogsRecyclerAdapter.DialogsListInteractionListener {

    private var adapter: DialogsRecyclerAdapter? = null;
    var rvDialogs: RecyclerView? = null

    override fun showDialogs(chapters: List<DialogResponse>?) {
        adapter?.setDialogs(chapters)
    }

    private var dialogs: List<DialogResponse>? = null

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

    override fun onDialogClick(dialog: DialogResponse) {
        val intent = Intent(activity, DialogActivity::class.java)
        intent.putExtra(DialogActivity.DIALOG, dialog)
        startActivity(intent)
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
