package com.leoart.koreanphrasebook.ui.notes

import com.leoart.koreanphrasebook.ui.models.Note

interface OnNoteClickListener {
    fun onDelete(note: Note)
    fun onEdit(note: Note)
}