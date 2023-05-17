package com.example.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteapp.model.Note

@Database(
    entities = [Note::class],
    version = 1, exportSchema = false
)
abstract class NoteDB : RoomDatabase() {

    abstract fun getNoteDao() : NoteDao

    companion object{
        @Volatile
        private var instance : NoteDB ? =null

        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDataBase(context).also{ instance = it}
        }

        private fun createDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDB::class.java,"note_db.db"
        ).build()


    }
}