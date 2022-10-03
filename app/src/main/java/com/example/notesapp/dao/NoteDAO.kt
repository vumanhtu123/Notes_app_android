package com.example.notesapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.data.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}