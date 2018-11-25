package com.leoart.koreanphrasebook.ui

import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.Auth
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.network.firebase.auth.FRAuth
import com.leoart.koreanphrasebook.data.parsers.alphabet.AlphabetParser
import com.leoart.koreanphrasebook.data.repository.DataInfoRepository
import com.leoart.koreanphrasebook.data.repository.models.EChapter
import com.leoart.koreanphrasebook.data.repository.models.EDialog
import com.leoart.koreanphrasebook.data.repository.models.EDictionary
import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.favourite.FavouriteFragment
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.search.SearchActivity
import com.leoart.koreanphrasebook.ui.sync.SyncModel
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment
import com.leoart.koreanphrasebook.utils.NetworkChecker
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : BaseActivity(), BottomMenu.BottomMenuListener, MainView {

    var auth: Auth? = null
    @Inject
    lateinit var analyticsManager: AnalyticsManager
    val syncDataList = DataInfoRepository.getInstance().getData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        analyticsManager.openChapterCategory("main screen")
        initUI()
        auth = FRAuth()
    }

    private fun initUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.action_chapters
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_dict -> dictSelected()
                R.id.action_favourite -> favouriteSelected()
                R.id.action_chapters -> chaptersSelected()
                R.id.action_dialogs -> dialogsSelected()
                R.id.action_info -> infoSelected()
            }
            true
        }
        chaptersSelected()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun hideBackArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun showBackArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun showNoNetworkFragment() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        this.replace(NoNetworkFragment.newInstance(), false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                openSearch()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        intent.action = Intent.ACTION_SEARCH
        startActivity(intent)
    }


    override fun isNetworkAvailable(): Boolean {
        return NetworkChecker(this).isNetworkAvailable
    }

    override fun dictSelected() {
        if (!isNetworkAvailable() && syncDataList != null && syncDataList!!.contains(SyncModel(EDictionary::class.java.simpleName, true))) {
            showNoNetworkFragment()
        } else {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            this.replace(VocabularyFragment.newInstance(), false)
        }
    }

    override fun favouriteSelected() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        this.replace(FavouriteFragment.newInstance(), false)
    }

    override fun chaptersSelected() {
        if (!isNetworkAvailable() && syncDataList != null && syncDataList!!.contains(SyncModel(EChapter::class.java.simpleName, true))) {
            showNoNetworkFragment()
        } else {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            this.replace(ChapterFragment.newInstance(this), false)
        }
    }

    override fun dialogsSelected() {
        if (!isNetworkAvailable() && syncDataList != null && syncDataList!!.contains(SyncModel(EDialog::class.java.simpleName, true))) {
            showNoNetworkFragment()
        } else {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            this.replace(DialogsFragment.newInstance(this), false)
        }
    }

    override fun infoSelected() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        this.replace(InfoFragment.newInstance(this), false)
    }

    override fun replace(fragment: BaseFragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.name)
        }
        transaction.commit()
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    companion object {
        const val TITLE = "title"
    }
}
