package com.leoart.koreanphrasebook.ui.dialogs.dialog

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.database.Observable
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
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainActivity
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.chapters.category.CategoriesViewModel
import com.leoart.koreanphrasebook.utils.NetworkChecker
import kotlinx.android.synthetic.main.activity_categories.*
import java.util.*

@SuppressLint("ValidFragment")
/**
 * Created by bogdan on 6/18/17.
 */
class DialogFragment : BaseFragment() {

    var dialog: DialogResponse? = null
    private val adapter = DialogMessagesRecyclerAdapter(ArrayList())
    private lateinit var model: DialogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_dialog, container, false)
        model = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(DialogViewModel::class.java)
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
                model.getDialog(it.uid).observe(this, Observer<List<EReplic>> {
                    it?.let { list ->
                        activity?.let {
                            if (list.isEmpty() && !NetworkChecker(it.applicationContext).isNetworkAvailable) {
                                viewStub.layoutResource = R.layout.no_internet
                                viewStub.inflate()
                            } else {
                                adapter.updateReplics(list)
                            }
                        }
                    }
                })
            }
        }

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