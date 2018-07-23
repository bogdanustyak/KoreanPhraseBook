package com.leoart.koreanphrasebook.ui.notes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.ViewModelFactory
import com.leoart.koreanphrasebook.ui.models.Note

class NotesFragment : BaseFragment() {


    private val adapter = NotesListAdapter()
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        initView(view)
        initViewModel()
        return view
    }

    private fun initView(view: View) {
        val listView = view.findViewById<RecyclerView>(R.id.notes_list)
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = adapter
        val addButton = view.findViewById<ConstraintLayout>(R.id.add_note)
        addButton.setOnClickListener({ openEditNote() })
    }

    private fun openEditNote() {
        viewModel.addNote(Note("title", "description"))
    }

    private fun initViewModel() {
        this.viewModel = ViewModelProviders
                .of(this, ViewModelFactory(this.requireContext()))
                .get(NotesViewModel::class.java)
        this.viewModel.notesData().observe(this, Observer<MutableList<Note>> {
            it?.let {
                adapter.refreshList(it)
            }
        })
        this.viewModel.fetchNotes()
    }

    companion object {
        fun newInstance(title: String): NotesFragment {
            val fragment = NotesFragment()
            fragment.title = title
            return fragment
        }
    }
}