package com.example.notesapp.utilities

import com.example.notesapp.data.Note

interface NoteClickListeners {
    fun noteClickUpDate(note: Note, position: Int)
}