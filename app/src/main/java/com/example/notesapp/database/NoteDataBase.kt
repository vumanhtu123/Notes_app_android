package com.example.notesapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.dao.NoteDAO
import com.example.notesapp.data.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: NoteDataBase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?:
        synchronized(LOCK) {
            INSTANCE ?: getDatabase(context).also { INSTANCE = it }
        }

        private fun getDatabase(context: Context): NoteDataBase =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDataBase::class.java,
                "note_database"
            ).allowMainThreadQueries().build()
    }
}