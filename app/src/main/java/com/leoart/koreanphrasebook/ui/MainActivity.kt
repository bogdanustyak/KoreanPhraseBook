package com.leoart.koreanphrasebook.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.BottomMenu
import com.leoart.koreanphrasebook.ui.MainView
import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.favourite.FavouriteFragment
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment


class MainActivity : AppCompatActivity(), BottomMenu.BottomMenuListener, MainView {

    var bottomMenu: BottomMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //        CategoryParser(baseContext).writeToFirebaseDB()
        //        val data = PhrasesParser(baseContext, "phrases.txt").parse()
        //        PhrasesRequest().writePhrases("category33", data)

        this.bottomMenu = BottomMenu(
                findViewById(R.id.iv_dict) as ImageView,
                findViewById(R.id.iv_favorite) as ImageView,
                findViewById(R.id.iv_chapters) as ImageView,
                findViewById(R.id.iv_dialogs) as ImageView,
                findViewById(R.id.iv_info) as ImageView,
                this
        )

        //        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        //
        //        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
        //            when (item.itemId) {
        //                R.id.action_dict ->
        //                    dictSelected()
        //                R.id.action_favourite ->
        //                    favouriteSelected()
        //                R.id.action_chapters ->
        //                    chaptersSelected()
        //                R.id.action_dialogs ->
        //                    dialogsSelected()
        //                R.id.action_info ->
        //                    infoSelected()
        //                else -> {
        //                    chaptersSelected()
        //                }
        //            }
        //            true
        //        }

        chaptersSelected()

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.main_content)
            if (fragment != null && fragment is BaseFragment) {
                this.title = fragment.title
            }
        }
    }

    override fun dictSelected() {
        this.replace(VocabularyFragment.newInstance(getString(R.string.menu_dict)))
    }

    override fun favouriteSelected() {
        this.replace(FavouriteFragment.newInstance(getString(R.string.menu_favourite), this))
    }

    override fun chaptersSelected() {
        this.replace(ChapterFragment.newInstance(getString(R.string.menu_chapters), this))
    }

    override fun dialogsSelected() {
        this.replace(DialogsFragment.newInstance(getString(R.string.menu_dialogs), this))
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
