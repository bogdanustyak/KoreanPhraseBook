package com.leoart.koreanphrasebook.ui.alphabet

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.ViewModelFactory

class AlphabetActivity : AppCompatActivity() {

    private val adapter = AlphabetAdapter()
    private lateinit var viewModel: AlphabetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiity_alphabet)
        initView()
        initToolbar()
        initViewModel()
        subscribeOnData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun subscribeOnData() {
        viewModel.lettersData().observe(this, Observer {
            it?.let {
                adapter.refreshList(it)
            }
        })
        viewModel.fetchLetters()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.alphabet_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.alphabet_chapter_name)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders
                .of(this, ViewModelFactory(this))
                .get(AlphabetViewModel::class.java)
    }

    private fun initView() {
        val lettersList = findViewById<RecyclerView>(R.id.letters_list)
        lettersList.adapter = adapter
        lettersList.layoutManager = GridLayoutManager(this, LETTERS_IN_ROW)
    }


    companion object {
        const val LETTERS_IN_ROW = 6
    }

}