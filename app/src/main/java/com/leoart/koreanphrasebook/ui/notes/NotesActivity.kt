package com.leoart.koreanphrasebook.ui.notes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.models.Note
import android.support.v7.widget.DividerItemDecoration



class NotesActivity : AppCompatActivity(), OnNoteClickListener {

    private lateinit var viewModel: NotesViewModel
    private val adapter = NotesListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notes)
        initView()
        initToolbar()
        initViewModel()
        subscribeOnViewModel()
        this.viewModel.fetchNotes()
    }

    override fun onEdit(note: Note) {
        openEditNote(note)
    }

    override fun onDelete(note: Note) {
        viewModel.deleteNote(note)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initView() {
        val listView = findViewById<RecyclerView>(R.id.notes_list)
        val layoutManager = LinearLayoutManager(this)
        listView.layoutManager = layoutManager
        listView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this,
                layoutManager.orientation)
        listView.addItemDecoration(dividerItemDecoration)
        val addButton = findViewById<ConstraintLayout>(R.id.add_note)
        addButton.setOnClickListener({ openCreateNote() })
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.notes_toolbar)
        toolbar.title = getString(R.string.notes_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun openCreateNote() {
        val intent = Intent(this, EditNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        this.finish()
    }

    private fun openEditNote(note: Note) {
        val intent = Intent(this, EditNoteActivity::class.java).apply {
            putExtra(EditNoteActivity.NOTE_KEY, note)
        }
        startActivity(intent)
    }

    private fun initViewModel() {
        this.viewModel = ViewModelProviders
                .of(this, ViewModelFactory(this))
                .get(NotesViewModel::class.java)
    }

    private fun subscribeOnViewModel() {
        this.viewModel.notesData().observe(this, Observer<List<Note>> {
            it?.let {
                adapter.refreshList(it)
            }
        })
    }
}