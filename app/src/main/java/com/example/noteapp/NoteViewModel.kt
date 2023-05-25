package com.example.noteapp

import android.app.Application
import androidx.lifecycle.*
import com.example.noteapp.database.NoteRepository
import com.example.noteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application,
                    val noteRepository: NoteRepository)
    : AndroidViewModel(application) {

//    private val _allNote : MutableLiveData<List<Note>> = MutableLiveData()
//    val allNote : LiveData<List<Note>>
//    get() = _allNote


    init {
        getNote()
    }
    fun getNote()=noteRepository.getAllNote()

    private fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.delete(note)
    }
    private fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insert(note)
    }
    private fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.update(note)
    }
}