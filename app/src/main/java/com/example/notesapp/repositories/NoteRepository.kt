package com.example.notesapp.repositories

import androidx.lifecycle.LiveData
import com.example.notesapp.dao.NoteDAO
import com.example.notesapp.data.Note

class NoteRepository(private val noteDao: NoteDAO) {
    val readAllData: LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun addNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}