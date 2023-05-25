package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.adapter.NotesAdapter
import com.example.noteapp.database.NoteDB
import com.example.noteapp.database.NoteRepository
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.model.Note

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NotesAdapter
    lateinit var selectedNote : Note
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        val noteRepository = NoteRepository(NoteDB(this))
        val vieModelProviderFactory = NoteViewModelFactory(application,noteRepository)
        noteViewModel = ViewModelProvider(this,vieModelProviderFactory)[NoteViewModel::class.java]

        noteViewModel.getNote().observe(this, Observer {  list ->
            list?.let {
                noteAdapter.differ.submitList(list)
            }
        })

    }

    private fun initUI() {
       binding.rv.setHasFixedSize(true)
       binding.rv.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
       noteAdapter = NotesAdapter(this,::onNoteClicked)

    }
    private fun onNoteClicked(noteResponse: Note){

    }
}