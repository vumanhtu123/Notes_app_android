package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.repositories.NoteRepository

class MyViewModelFactory(
    private var application: Application,
    private val noteRepository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateViewModel(application, noteRepository) as T
    }
}