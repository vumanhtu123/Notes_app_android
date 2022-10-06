package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.data.Note
import com.example.notesapp.database.NoteDataBase
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utilities.Coroutines

class CreateViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository: NoteRepository

    init {
        val dao = NoteDataBase.getDatabase(application).getNoteDao()
        noteRepository = NoteRepository(dao)
    }

    fun addNote(note: Note) = Coroutines.io {
        noteRepository.addNote(note)
    }
    fun deleteNote(note: Note) = Coroutines.io {
        noteRepository.deleteNote(note)
    }
    fun updateNote(note: Note) = Coroutines.io {
        noteRepository.updateNote(note)
    }
    fun getNote():LiveData<List<Note>> = noteRepository.getAllNotes()

    override fun onCleared() {
        super.onCleared()
    }
}