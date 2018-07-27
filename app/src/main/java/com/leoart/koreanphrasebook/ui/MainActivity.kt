package com.leoart.koreanphrasebook.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.Auth
import com.leoart.koreanphrasebook.data.network.firebase.auth.FRAuth
import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.favourite.FavouriteFragment
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.search.SearchActivity
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment
import com.leoart.koreanphrasebook.utils.NetworkChecker
import com.leoart.koreanphrasebook.utils.SoftKeyboard


class MainActivity : AppCompatActivity(), BottomMenu.BottomMenuListener, MainView {

//    private var bottomMenu: BottomMenu? = null
    var auth: Auth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        auth = FRAuth()
    }

    private fun initUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //        CategoryParser(baseContext).writeToFirebaseDB()
        //        val data = PhrasesParser(baseContext, "phrases.txt").parse()
        //        PhrasesRequest().writePhrases("category33", data)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_dict -> dictSelected()
                R.id.action_favourite -> favouriteSelected()
                R.id.action_chapters -> chaptersSelected()
                R.id.action_dialogs -> dialogsSelected()
                R.id.action_info -> infoSelected()
            }
            true
        }
//        this.bottomMenu = BottomMenu(
//                findViewById<ImageView>(R.id.iv_dict),
//                findViewById<ImageView>(R.id.iv_favorite),
//                findViewById<ImageView>(R.id.iv_chapters),
//                findViewById<ImageView>(R.id.iv_dialogs),
//                findViewById<ImageView>(R.id.iv_info),
//                this
//        )
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
        if(addToBackStack){
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
