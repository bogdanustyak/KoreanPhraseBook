package com.leoart.koreanphrasebook.ui.notes

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.models.Note

class EditNoteActivity : AppCompatActivity() {

    private var note: Note? = null
    private lateinit var etTitle: EditText
    private lateinit var etDesc: EditText
    private lateinit var viewModel: EditNoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        getParams()
        initViewModel()
        initView()
        initToolbar()
        setNoteData()
    }

    override fun onBackPressed() {
        if (titleFilled()) {
            writeChanges()
        }
        finish()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.edit_note_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.edit_note_title)
    }

    private fun initViewModel() {
        this.viewModel = ViewModelProviders
                .of(this, ViewModelFactory(this))
                .get(EditNoteViewModel::class.java)
    }

    private fun getParams() {
        intent?.let {
            val serializable = it.getSerializableExtra(NOTE_KEY)
            serializable?.let {
                this.note = it as Note
            }
        }
    }

    private fun initView() {
        etTitle = findViewById(R.id.note_title)
        etDesc = findViewById(R.id.note_desc)
    }

    private fun setNoteData() {
        note?.let {
            etTitle.setText(it.title, TextView.BufferType.EDITABLE)
            etDesc.setText(it.description, TextView.BufferType.EDITABLE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun writeChanges() {
        note?.let {
            viewModel.updateNoteInDB(Note(it.uid, etTitle.text.toString(), etDesc.text.toString()))
        } ?: run {
            viewModel.writeNoteToDB(Note("", etTitle.text.toString(), etDesc.text.toString()))
        }
    }

    private fun titleFilled(): Boolean {
        return !etTitle.text.isEmpty()
    }

    companion object {
        const val NOTE_KEY = "note"
    }
}