package com.example.notesapp.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notesapp.R
import com.example.notesapp.activities.main.MainActivity
import com.example.notesapp.databinding.ActivitySplashBinding
import com.example.notesapp.utilities.Coroutines
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Coroutines.io {
            delay(2000)
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
    }
}