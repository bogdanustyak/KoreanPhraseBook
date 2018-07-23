package com.leoart.koreanphrasebook.ui.notes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.models.Note

class NotesListAdapter : RecyclerView.Adapter<NotesListAdapter.ViewHolder>(){

    private val notesList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val holder = ViewHolder(view)
        holder.delButton.setOnClickListener {
            Toast.makeText(parent.context, "DELETE", Toast.LENGTH_SHORT).show()
        }
        return holder
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    fun refreshList(notes : MutableList<Note>){
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title = view.findViewById<TextView>(R.id.note_title)
        val delButton = view.findViewById<ImageView>(R.id.ic_delete)

        fun bind(note : Note){
            title.text = note.title
        }
    }

}