package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(var application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}