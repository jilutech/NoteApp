package com.example.noteapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id : Int ?,
    @ColumnInfo("title") val title : String ?,
    @ColumnInfo("note") val notes : String ?,
    @ColumnInfo("date") val dates : String ?

)
