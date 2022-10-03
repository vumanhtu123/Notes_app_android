package com.example.notesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    @ColumnInfo(name = "title")
    private val title: String,
    @ColumnInfo(name = "date_time")
    private val datetime: String,
    @ColumnInfo(name = "subtitle")
    private val subtitle: String,
    @ColumnInfo(name = "note_text")
    private val noteText: String,
    @ColumnInfo(name = "image_path")
    private val imagePath: String,
    @ColumnInfo(name = "color")
    private val color: String,
    @ColumnInfo(name = "web_link")
    private val webLink: String

)
