package com.leoart.koreanphrasebook

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.leoart.koreanphrasebook.ui.BottomMenu
import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.dialogs.models.Dialog
import com.leoart.koreanphrasebook.ui.info.InfoFragment
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment


class MainActivity : AppCompatActivity(), BottomMenu.BottomMenuListener {

    var bottomMenu: BottomMenu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }

    override fun dictSelected() {
        title = getString(R.string.menu_dict)
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content,
                        VocabularyFragment.newInstance())
                .commit()
    }

    override fun favouriteSelected() {
        title = getString(R.string.menu_favourite)
    }

    override fun chaptersSelected() {
        title = getString(R.string.menu_chapters)
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content,
                        ChapterFragment.newInstance())
                .commit()
    }

    override fun dialogsSelected() {
        title = getString(R.string.menu_dialogs)
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content,
                        DialogsFragment.newInstance())
                .commit()
    }

    override fun infoSelected() {
        title = getString(R.string.menu_info)
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content,
                        InfoFragment.newInstance())
                .commit()
    }

    private val dialogs: List<Dialog>
        get() = DemoDataProvider().dialogs


}
