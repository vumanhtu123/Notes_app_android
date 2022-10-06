package com.example.notesapp.activities.createnotes

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ActivityCreateNotesBinding
import com.example.notesapp.viewmodels.CreateViewModel
import com.example.notesapp.viewmodels.MyViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.text.SimpleDateFormat
import java.util.*

class CreateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNotesBinding
    private val createViewModel: CreateViewModel by viewModels {
        MyViewModelFactory(application)
    }
    private val formatter =
        SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(
            Date()
        )
    private var select = "#333333"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.textDateTime.text = formatter
        binding.imgSave.setOnClickListener {
            insertDataToDataBase()
        }
        initMiscellaneous()
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
                select,
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

    private fun initMiscellaneous() {
        val from = BottomSheetBehavior.from(binding.layoutMiscellaneous.layoutMiscellaneous)
        binding.layoutMiscellaneous.layoutMiscellaneous.setOnClickListener {
            Log.e("abc", "onclick successfully")
            if (from.state != BottomSheetBehavior.STATE_EXPANDED) {
                from.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                from.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        binding.layoutMiscellaneous.viewColor1.setOnClickListener {
            select = "#333333"
            binding.layoutMiscellaneous.imageColor1.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor2.setOnClickListener {
            select = "#FFEB3B"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor3.setOnClickListener {
            select = "#BC1B02"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor4.setOnClickListener {
            select = "#2196F3"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor5.setOnClickListener {
            select = "#FF000000"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(R.drawable.ic_done)
            setSubtitleColor()
        }
    }

    private fun setSubtitleColor() {
        var gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(Color.parseColor(select))
        binding.viewSubtitle.background = gradientDrawable
        Color.TRANSPARENT
    }
}