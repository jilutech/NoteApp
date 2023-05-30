package com.example.noteapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.databinding.ActivityAddNoteBinding
import com.example.noteapp.model.Note
import java.text.SimpleDateFormat
import java.util.Date

class AddNote : AppCompatActivity() {

    private lateinit var binding : ActivityAddNoteBinding
    private lateinit var note : Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imUpdate.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etNote.text.toString()
            val formater = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

            if (title.isNotEmpty() && desc.isNotEmpty() ){
                note = Note(null,title,desc,formater.format(Date()))
                val  intent = Intent()
                intent.putExtra("note" , note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
               Toast.makeText(this@AddNote,"Fields should fill",Toast.LENGTH_LONG).show()
            }

        }
    }
}