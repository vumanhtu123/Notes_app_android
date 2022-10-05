package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.data.Note
import com.example.notesapp.database.NoteDataBase
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utilities.Coroutines

class CreateViewModel(application: Application) : AndroidViewModel(application) {
    private val getallNotes: LiveData<List<Note>>
    private val noteRepository: NoteRepository

    init {
        val dao = NoteDataBase.getDatabase(application).getNoteDao()
        noteRepository = NoteRepository(dao)
        getallNotes = noteRepository.readAllData
    }

    fun addNote(note: Note) = Coroutines.io {
        noteRepository.addNote(note)
    }

    override fun onCleared() {
        super.onCleared()
    }
}