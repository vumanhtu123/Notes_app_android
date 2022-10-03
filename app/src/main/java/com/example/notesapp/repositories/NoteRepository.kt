package com.example.notesapp.repositories

import androidx.lifecycle.LiveData
import com.example.notesapp.dao.NoteDAO
import com.example.notesapp.data.Note

class NoteRepository(private val noteDao:NoteDAO) {
    val readAllData: LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun addNote(note: Note){
       noteDao.insertNote(note)
    }
}