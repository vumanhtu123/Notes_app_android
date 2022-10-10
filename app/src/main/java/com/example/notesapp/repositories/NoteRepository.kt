package com.example.notesapp.repositories

import androidx.lifecycle.LiveData
import com.example.notesapp.dao.NoteDAO
import com.example.notesapp.data.Note

class NoteRepository(private val noteDao: NoteDAO) {
//    val readAllData: LiveData<List<Note>> = noteDao.getAllNotes()
    fun getAllNotes():LiveData<List<Note>>{
        return noteDao.getAllNotes()
    }
    fun addNote(note: Note) {
        noteDao.insertNote(note)
    }

    fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }
}