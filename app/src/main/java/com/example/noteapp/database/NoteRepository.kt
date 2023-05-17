package com.example.noteapp.database

import com.example.noteapp.model.Note

class NoteRepository(private var noteDB: NoteDB) {
    fun getAllNote()=noteDB.getNoteDao().getAllNote()


    suspend fun insert(note: Note)=noteDB.getNoteDao().insert(note)

    suspend fun delete(note: Note)=noteDB.getNoteDao().delete(note)

    suspend fun update(note: Note)=noteDB.getNoteDao().update(note.id,note.title,note.notes)



}