package com.example.notesapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    @Update
    fun updateNote(note: Note)

    @Query("SELECT * FROM NOTES_TABLE WHERE title LIKE :query OR subtitle LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}