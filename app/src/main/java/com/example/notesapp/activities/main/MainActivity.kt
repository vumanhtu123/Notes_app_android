package com.example.notesapp.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.activities.createnotes.CreateNotesActivity
import com.example.notesapp.adapters.NoteAdapter
import com.example.notesapp.data.Note
import com.example.notesapp.database.NoteDataBase
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utilities.CreateDialog
import com.example.notesapp.utilities.NoteClickListeners
import com.example.notesapp.viewmodels.CreateViewModel
import com.example.notesapp.viewmodels.MyViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NoteClickListeners,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CreateViewModel
    private var adapters = NoteAdapter(this, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        binding.addMyNotes.setOnClickListener {
            startActivity(Intent(applicationContext, CreateNotesActivity::class.java))
        }
        binding.searchView.setOnQueryTextListener(this)
        setupObserve()
        binding.addLink.setOnClickListener {
            addLinkUrl()
        }
    }

    private fun addLinkUrl() {
        CreateDialog(R.layout.layout_add_url, this, 0).also { dialog ->
            val urlink = dialog.findViewById<EditText>(R.id.edAddURL)
            dialog.findViewById<View>(R.id.textAdd).setOnClickListener {
                if (urlink.text.isEmpty() && urlink != null) {
                    Snackbar.make(binding.root, "No URL", Snackbar.LENGTH_LONG).show()
                    dialog.dismiss()
                } else {
                    val intent = Intent(this, CreateNotesActivity::class.java)
                    intent.putExtra("isAddUrl", true)
                    intent.putExtra("url", urlink.text.toString())
                    startActivity(intent)
                    dialog.dismiss()
                }
            }
            dialog.findViewById<View>(R.id.textCancel).setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun setupObserve() {
        viewModel.getNote().observe(this) { noteList ->
            for (i in noteList) {
                Log.e("eee", "onCreateView : $i")
            }
            binding.notesRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapters.differ.submitList(noteList)
            binding.notesRecyclerView.adapter = adapters
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(this) { list ->
            adapters.differ.submitList(list)
        }
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
            noteDataBase = NoteDataBase(this)
        )
        val viewModelFactory =
            MyViewModelFactory(
                application, noteRepository
            )
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateViewModel::class.java]
    }
}