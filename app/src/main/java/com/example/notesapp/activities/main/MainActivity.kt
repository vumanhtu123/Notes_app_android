package com.example.notesapp.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.activities.createnotes.CreateNotesActivity
import com.example.notesapp.adapters.NoteRecyclerViewAdapter
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.utilities.NoteClickListeners
import com.example.notesapp.viewmodels.CreateViewModel

class MainActivity : AppCompatActivity(), NoteClickListeners {
    private var REQUEST_CODE_ADD = 1
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CreateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addMyNotes.setOnClickListener {
            startActivity(Intent(applicationContext, CreateNotesActivity::class.java))
        }
        setupObserve()
    }

    private fun setupObserve() {
        viewModel.getNote().observe(this) { noteList ->
            for (i in noteList) {
                Log.e("eee", "onCreateView : $i")
            }
            binding.notesRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.notesRecyclerView.adapter = NoteRecyclerViewAdapter(this, noteList, this)
        }
    }

    override fun noteClickUpDate(note: Note, position: Int) {
        val bundle = Bundle()
        val intent = Intent(this, CreateNotesActivity::class.java)
        bundle.putParcelable("note", note)
        intent.putExtra("isViewOrUpdate", true)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}