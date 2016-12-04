package com.leoart.koreanphrasebook

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.google.firebase.database.*

import com.leoart.koreanphrasebook.ui.chapters.ChapterFragment
import com.leoart.koreanphrasebook.ui.chapters.models.Chapter
import com.leoart.koreanphrasebook.data.parsers.vocabulary.DictionaryParser
import com.leoart.koreanphrasebook.ui.ViewPagerAdapter
import com.leoart.koreanphrasebook.ui.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.ui.dialogs.models.Dialog
import com.leoart.koreanphrasebook.ui.vocabulary.VocabularyFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val viewPager = findViewById(R.id.viewpager) as ViewPager
        setupViewPager(viewPager)

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)


        //DictionaryParser(baseContext).writeToFirebaseDB()


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ChapterFragment.newInstance(), "Chapter")
        adapter.addFragment(DialogsFragment.newInstance(), "Dialogs")
        adapter.addFragment(VocabularyFragment(), "Vocabulary")
        viewPager.adapter = adapter
    }

    private val dialogs: List<Dialog>
        get() = DemoDataProvider().dialogs


}
