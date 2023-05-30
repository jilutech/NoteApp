package com.example.noteapp

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
        noteAdapter.setOnItemClick {
            Log.e(".in","kkk")
        }

    }

    private fun initUI() {
        noteAdapter= NotesAdapter(this,::onNoteClicked)
        binding.rv.apply {
            adapter=noteAdapter
            layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        }


        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->

            if (result.resultCode == Activity.RESULT_OK){
                val node = result.data?.getSerializableExtra("note") as Note
                if (node != null){
                    noteViewModel.insert(node)
                }

            }
        }

        binding.fbAddNote.setOnClickListener{
            val intent = Intent(this,AddNote::class.java)
            getContent.launch(intent)
        }

    }
    private fun onNoteClicked(noteResponse: Note){
       Log.e(".....", noteResponse.title.toString())
    }
}