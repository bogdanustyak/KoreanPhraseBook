package com.leoart.koreanphrasebook.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.backgroundColor


class MainActivity : AppCompatActivity(), BottomMenu.BottomMenuListener, MainView,
        SearchView.OnQueryTextListener {

    var bottomMenu: BottomMenu? = null
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

        this.bottomMenu = BottomMenu(
                findViewById<ImageView>(R.id.iv_dict),
                findViewById<ImageView>(R.id.iv_favorite),
                findViewById<ImageView>(R.id.iv_chapters),
                findViewById<ImageView>(R.id.iv_dialogs),
                findViewById<ImageView>(R.id.iv_info),
                this
        )
        if (NetworkChecker(this).isNetworkAvailable) {
            chaptersSelected()
        } else {
            showNoNetworkFragment()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.main_content)
            if (fragment != null && fragment is BaseFragment) {
                this.title = fragment.title
            }
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
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        openSearch(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                print("cliiiiick search")
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
            this.replace(VocabularyFragment.newInstance(getString(R.string.menu_dict)))
        } else {
            showNoNetworkFragment()
        }
    }

    override fun favouriteSelected() {
        if (NetworkChecker(this).isNetworkAvailable) {
            this.replace(FavouriteFragment.newInstance(getString(R.string.menu_favourite), this))
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
            this.replace(ChapterFragment.newInstance(getString(R.string.menu_chapters), this))
        } else {
            showNoNetworkFragment()
        }
    }

    override fun dialogsSelected() {
        if (NetworkChecker(this).isNetworkAvailable) {
            this.replace(DialogsFragment.newInstance(getString(R.string.menu_dialogs), this))
        } else {
            showNoNetworkFragment()
        }
    }

    override fun infoSelected() {
        this.replace(InfoFragment.newInstance(getString(R.string.menu_info)))
    }

    override fun replace(fragment: BaseFragment) {
        title = fragment.title
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit()
    }

    override fun add(fragment: BaseFragment) {
        this.add(fragment, fragment.title)
    }

    override fun add(fragment: BaseFragment, title: String) {
        this.title = title
        supportFragmentManager.beginTransaction()
                .add(R.id.main_content, fragment, title)
                .addToBackStack(title)
                .commit()
    }
}
