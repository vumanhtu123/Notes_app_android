package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.data.Note
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utilities.Coroutines

class CreateViewModel(application: Application, private val noteRepository: NoteRepository) :
    AndroidViewModel(application) {

    fun addNote(note: Note) = Coroutines.io {
        noteRepository.addNote(note)
    }

    fun deleteNote(note: Note) = Coroutines.io {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = Coroutines.io {
        noteRepository.updateNote(note)
    }

    fun getNote() = noteRepository.getAllNotes()

    fun searchNote(query: String?) =
        noteRepository.searchNote(query)
}