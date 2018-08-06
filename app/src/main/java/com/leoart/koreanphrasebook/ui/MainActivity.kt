package com.leoart.koreanphrasebook.ui

import android.app.SearchManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.Auth
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.network.firebase.auth.FRAuth
import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.favourite.FavouriteFragment
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.refresh_data.DataRefreshIntentService
import com.leoart.koreanphrasebook.ui.search.SearchActivity
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment
import com.leoart.koreanphrasebook.utils.NetworkChecker
import com.leoart.koreanphrasebook.utils.SoftKeyboard
import javax.inject.Inject
import dagger.android.AndroidInjection
import org.jetbrains.anko.contentView
import android.content.IntentFilter




class MainActivity : BaseActivity(), BottomMenu.BottomMenuListener, MainView {

    var auth: Auth? = null

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
        analyticsManager.openChapterCategory("main screen")
        checkPersistedData()
        registerRefreshReceiver()
        initUI()
        auth = FRAuth()
    }

    private fun registerRefreshReceiver() {
        val filter = IntentFilter(DataRefreshIntentService.ACTION_RESP)
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        val receiver = RefreshBroadcastReceiver()
        registerReceiver(receiver, filter)
    }

    private fun initUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //        CategoryParser(baseContext).writeToFirebaseDB()
        //        val data = PhrasesParser(baseContext, "phrases.txt").parse()
        //        PhrasesRequest().writePhrases("category33", data)
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
        if (NetworkChecker(this).isNetworkAvailable) {
            chaptersSelected()
        } else {
            showNoNetworkFragment()
        }
    }

    private fun showNoNetworkFragment() {
        this.replace(NoNetworkFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as? SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setIconifiedByDefault(true)
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchView.isIconified = true
                    searchView.clearFocus()
                    (menu.findItem(R.id.action_search)).collapseActionView()
                    SoftKeyboard(this@MainActivity).hide()
                    openSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                onSearchRequested()
            }
        }
        return true
    }

    private fun openSearch(query: String) {
        val intent = Intent(this, SearchActivity::class.java)
        intent.action = Intent.ACTION_SEARCH
        intent.putExtra(SearchManager.QUERY, query)
        startActivity(intent)
    }

    override fun dictSelected() {
        if (NetworkChecker(this).isNetworkAvailable) {
            this.replace(VocabularyFragment.newInstance(), false)
        } else {
            showNoNetworkFragment()
        }
    }

    override fun favouriteSelected() {
        if (NetworkChecker(this).isNetworkAvailable) {
            this.replace(FavouriteFragment.newInstance(), false)
        } else {
            showNoNetworkFragment()
        }
//        if (userSignedIn) {
//
//        } else {
//            this.replace(AuthFragment.newInstance(getString(R.string.auth), this))
//        }
    }

    override fun chaptersSelected() {
        if (NetworkChecker(this).isNetworkAvailable) {
            this.replace(ChapterFragment.newInstance(this), false)
        } else {
            showNoNetworkFragment()
        }
    }

    override fun dialogsSelected() {
        if (NetworkChecker(this).isNetworkAvailable) {
            this.replace(DialogsFragment.newInstance(this), false)
        } else {
            showNoNetworkFragment()
        }
    }

    override fun infoSelected() {
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

    private fun checkPersistedData() {
        val checkIntent = Intent(this, DataRefreshIntentService::class.java)
        checkIntent.putExtra(DataRefreshIntentService.ACTION_TYPE, DataRefreshIntentService.CHECK_IF_DB_IS_EMPTY)
        startService(checkIntent)
    }

    inner class RefreshBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when(intent.getStringExtra(DataRefreshIntentService.ACTION_TYPE)){
                DataRefreshIntentService.CHECK_IF_DB_IS_EMPTY -> receiveIfEmpty(intent)
                DataRefreshIntentService.REFRESH_DB -> receiveRefreshed()
                DataRefreshIntentService.REFRESH_DB_ERROR -> showError()
            }
        }

        private fun showError() {

        }

        private fun receiveRefreshed() {

        }

        private fun receiveIfEmpty(intent: Intent) {
            val isEmpty = intent.getBooleanExtra(DataRefreshIntentService.IS_EMPTY, false)
            if(isEmpty){
                Snackbar.make(findViewById(android.R.id.content), "db is empty", Snackbar.LENGTH_LONG).show()
            }else{
                Snackbar.make(findViewById(android.R.id.content), "db is filled", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val TITLE = "title"
    }
}
