package com.example.notesapp.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapp.activities.createnotes.CreateNotesActivity
import com.example.notesapp.adapters.NoteRecyclerViewAdapter
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.viewmodels.CreateViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CreateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addMyNotes.setOnClickListener {
            startActivity(Intent(applicationContext, CreateNotesActivity::class.java))
        }
        viewModel.getNote().observe(this) { noteList ->
            for (i in noteList) {
                Log.e("eee", "onCreateView : $i")
            }
            binding.notesRecyclerView.layoutManager = GridLayoutManager(this,2)
            binding.notesRecyclerView.adapter = NoteRecyclerViewAdapter(this,noteList)
        }
    }
}