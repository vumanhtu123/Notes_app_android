package com.example.notesapp.repositories

import com.example.notesapp.data.Note
import com.example.notesapp.database.NoteDataBase

class NoteRepository(private val noteDataBase: NoteDataBase) {
    //    val readAllData: LiveData<List<Note>> = noteDao.getAllNotes()
    fun getAllNotes() = noteDataBase.getNoteDao().getAllNotes()

    fun addNote(note: Note) = noteDataBase.getNoteDao().insertNote(note)

    fun updateNote(note: Note) = noteDataBase.getNoteDao().updateNote(note)

    fun deleteNote(note: Note) = noteDataBase.getNoteDao().deleteNote(note)

    fun searchNote(query: String?) = noteDataBase.getNoteDao().searchNote(query)
}