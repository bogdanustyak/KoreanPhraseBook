package com.leoart.koreanphrasebook.ui.notes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.models.Note

class NotesListAdapter(private val listener: OnNoteClickListener) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private val notesList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        val holder = ViewHolder(view)
        holder.delButton.setOnClickListener {
            listener.onDelete(holder.note)
        }
        holder.itemView.setOnClickListener {
            listener.onEdit(holder.note)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    fun refreshList(notes: List<Note>) {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.note_title)
        val delButton = view.findViewById<ImageView>(R.id.ic_delete)
        lateinit var note: Note

        fun bind(note: Note) {
            this.note = note
            title.text = note.title
        }
    }

}