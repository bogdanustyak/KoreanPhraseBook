package com.leoart.koreanphrasebook.ui.info

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.chapters.InfoRecyclerAdapter
import com.leoart.koreanphrasebook.ui.notes.NotesFragment

/**
 * Created by bogdan on 6/14/17.
 */
class InfoFragment : BaseFragment(), InfoRecyclerAdapter.InfoInteractionListener {

    private lateinit var mainView : MainView

    companion object {
        fun newInstance(title: String, mainView: MainView): InfoFragment {
            val fragment = InfoFragment()
            fragment.title = title
            fragment.mainView = mainView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        val rvInfo = view.findViewById<RecyclerView>(R.id.rv_info)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvInfo.layoutManager = layoutManager
        rvInfo.itemAnimator = DefaultItemAnimator()
        rvInfo.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        val adapter = InfoRecyclerAdapter(infoItems(), this)
        rvInfo.adapter = adapter
        return view
    }

    private fun infoItems(): List<InfoItem>? {
        val items = ArrayList<InfoItem>()
        items.add(InfoItem(getString(R.string.share)))
        items.add(InfoItem(getString(R.string.rate)))
        items.add(InfoItem(getString(R.string.send_email)))
        items.add(InfoItem(getString(R.string.settings)))
        items.add(InfoItem(getString(R.string.notes_title)))
        return items
    }

    //todo
    override fun onItemClick(item: InfoItem) {
        when (item.name) {
            getString(R.string.share) ->
                Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
            getString(R.string.rate) ->
                Toast.makeText(context, "Rate", Toast.LENGTH_SHORT).show()
            getString(R.string.send_email) ->
                Toast.makeText(context, "Send email", Toast.LENGTH_SHORT).show()
            getString(R.string.settings) ->
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            getString(R.string.notes_title) -> openNotesScreen()
        }
    }

    private fun openNotesScreen() {
        mainView.add(NotesFragment.newInstance(getString(R.string.notes_title)))
    }
}