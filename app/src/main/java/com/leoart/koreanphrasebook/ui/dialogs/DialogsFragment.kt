package com.leoart.koreanphrasebook.ui.dialogs

import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.KoreanPhrasebookApp
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManagerImpl
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.analytics.ScreenNavigator
import com.leoart.koreanphrasebook.data.network.firebase.dialogs.models.DialogResponse
import com.leoart.koreanphrasebook.data.repository.DataInfoRepository
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.data.repository.models.EReplic
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.NoNetworkFragment
import com.leoart.koreanphrasebook.ui.dialogs.dialog.DialogFragment
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment
import com.leoart.koreanphrasebook.utils.NetworkChecker
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DialogsFragment : BaseFragment(), DialogsView,
        DialogsRecyclerAdapter.DialogsListInteractionListener {

    private lateinit var mainView: MainView
    private var adapter: DialogsRecyclerAdapter? = null
    var rvDialogs: RecyclerView? = null
    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun showDialogs(chapters: List<DialogResponse>?) {
            chapters?.let { itChapters ->
                    adapter?.setDialogs(chapters)
        }
    }

    private var dialogs: List<DialogResponse>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dialogs, container, false)

        rvDialogs = view.findViewById<RecyclerView>(R.id.rv_dialogs)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvDialogs?.layoutManager = layoutManager
        rvDialogs?.itemAnimator = DefaultItemAnimator()
        rvDialogs?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        adapter = DialogsRecyclerAdapter(dialogs, this)
        rvDialogs?.adapter = adapter
        analyticsManager.onOpenScreen(ScreenNavigator.DIALOGS_SCREEN.screenName)
        DialogsPresenter(
                this,
                view.context
        ).requestDialogs()
        return view
    }

    override fun onResume() {
        super.onResume()
        (context as MainView).setTitle(getString(R.string.dialogs))
    }

    override fun onDialogClick(dialog: DialogResponse) {
        this.mainView?.let {
            analyticsManager.openDialog(dialog.name)
            val syncInfo = DataInfoRepository.getInstance().getData()
            if (!mainView.isNetworkAvailable() && syncInfo!= null && syncInfo.contains(SyncModel(EReplic::class.java.simpleName, true))) {
                mainView.replace(NoNetworkFragment.newInstance())
            } else {
                it.replace(DialogFragment.newInstance(dialog.name, dialog))
            }

        }
    }

    companion object {

        fun newInstance(mainView: MainView): DialogsFragment {
            val fragment = DialogsFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mainView = mainView
            return fragment
        }
    }
}