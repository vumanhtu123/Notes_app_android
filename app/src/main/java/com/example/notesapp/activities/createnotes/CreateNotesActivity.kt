package com.example.notesapp.activities.createnotes

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ActivityCreateNotesBinding
import com.example.notesapp.viewmodels.CreateViewModel
import com.example.notesapp.viewmodels.MyViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class CreateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNotesBinding
    private val createViewModel: CreateViewModel by viewModels {
        MyViewModelFactory(application)
    }
    private val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(
        Date()
    )!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.textDateTime.text = formatter.toString()
        binding.imgSave.setOnClickListener {
            insertDataToDataBase()
        }
    }

    private fun insertDataToDataBase() {
        val title = binding.edNoteTitle.text.toString()
        val subTitle = binding.edNoteSubTitle.text.toString()
        val noteMain = binding.edNoteMain.text.toString()
        if (checkNote(title, subTitle, noteMain)) {
            //Create Note Object
            val note = Note(
                0,
                title,
                formatter,
                subTitle,
                noteMain,
                null.toString(),
                null.toString(),
                null.toString()
            )
            createViewModel.addNote(note)
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Successfully fail", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun checkNote(title: String, subTitle: String, noteMain: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(subTitle) && TextUtils.isEmpty(
            noteMain
        ))
    }
}