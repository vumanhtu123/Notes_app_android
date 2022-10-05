package com.example.notesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "date_time")
    val datetime: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "note_text")
    val noteText: String,
    @ColumnInfo(name = "image_path")
    val imagePath: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "web_link")
    val webLink: String

)
