package com.leoart.koreanphrasebook.ui.info

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.analytics.ScreenNavigator
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.chapters.InfoRecyclerAdapter
import com.leoart.koreanphrasebook.ui.notes.NotesActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by bogdan on 6/14/17.
 */
class InfoFragment : BaseFragment(), InfoRecyclerAdapter.InfoInteractionListener {

    private lateinit var mainView: MainView
    @Inject
    lateinit var analyticsManager: AnalyticsManager

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
        analyticsManager.onOpenScreen(ScreenNavigator.INFO_SCREEN.screenName)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as MainView).setTitle(getString(R.string.menu_info))
        AndroidSupportInjection.inject(this)
    }

    private fun infoItems(): List<InfoItem>? {
        val items = ArrayList<InfoItem>()
        items.add(InfoItem(getString(R.string.share)))
        items.add(InfoItem(getString(R.string.rate)))
        items.add(InfoItem(getString(R.string.send_email)))
        items.add(InfoItem(getString(R.string.notes_title)))
        items.add(InfoItem(getString(R.string.about)))
        return items
    }

    //todo
    override fun onItemClick(item: InfoItem) {
        when (item.name) {
            getString(R.string.share) -> share()
            getString(R.string.rate) ->
                Toast.makeText(context, "Rate", Toast.LENGTH_SHORT).show()
            getString(R.string.notes_title) -> openNotesScreen()
            getString(R.string.send_email) -> sendEmail()
            getString(R.string.about) -> about()
        }
    }

    private fun openNotesScreen() {
        analyticsManager.onWriteNote()
        startActivity(Intent(context, NotesActivity::class.java))
    }

    private fun share() {
        analyticsManager.onShare()
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        sendIntent.type = "text/plain"
        try {
            startActivity(Intent.createChooser(sendIntent, getString(R.string.share_title)))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this@InfoFragment.context, getString(R.string.share_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmail() {
        analyticsManager.onSendEmail()
        val mailIntent = Intent(Intent.ACTION_SEND)
        mailIntent.type = "message/rfc822"
        mailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.dev_mail)))
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
        mailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text))
        try {
            startActivity(Intent.createChooser(mailIntent, getString(R.string.email_share_title)))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this@InfoFragment.context, getString(R.string.share_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun about() {
        analyticsManager.onAbout()
        val alertDialog = AlertDialog.Builder(this@InfoFragment.context, R.style.AboutDialogTheme).create()
        alertDialog.setTitle(getString(R.string.about))
        alertDialog.setMessage(getString(R.string.about_info))
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }

    companion object {
        fun newInstance(mainView: MainView): InfoFragment {
            val fragment = InfoFragment()
            fragment.mainView = mainView
            return fragment
        }
    }
}