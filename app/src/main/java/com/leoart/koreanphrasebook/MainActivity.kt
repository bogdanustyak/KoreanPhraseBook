package com.leoart.koreanphrasebook

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.google.firebase.database.*

import com.leoart.koreanphrasebook.chapters.ChapterFragment
import com.leoart.koreanphrasebook.chapters.models.Chapter
import com.leoart.koreanphrasebook.dialogs.DialogsFragment
import com.leoart.koreanphrasebook.dialogs.models.Dialog
import com.leoart.koreanphrasebook.vocabulary.VocabularyFragment

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


        //        try {
//            val writer = DialogsWriter()
//            val dialog = DialogsStream(assets.open("dialog1.txt")).parse()
//            for (message in dialog.getMessages()) {
//                writer.addReplica("dialog5", message)
//                println(message.toString())
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }

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
