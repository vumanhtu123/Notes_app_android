package com.example.notesapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "date_time")
    val datetime: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "note_text")
    val noteText: String,
    @ColumnInfo(name = "image_path")
    var imagePath: String,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "web_link")
    var webLink: String

) : Parcelable
