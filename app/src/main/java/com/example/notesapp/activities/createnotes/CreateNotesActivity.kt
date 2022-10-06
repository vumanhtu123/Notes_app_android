package com.example.notesapp.activities.createnotes

import android.app.Activity
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ActivityCreateNotesBinding
import com.example.notesapp.viewmodels.CreateViewModel
import com.example.notesapp.viewmodels.MyViewModelFactory
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.InputStream
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
    private var selectColor = "#333333"
    private var selectImage = ""

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
                selectImage,
                selectColor,
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
            selectColor = "#333333"
            binding.layoutMiscellaneous.imageColor1.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor2.setOnClickListener {
            selectColor = "#FFEB3B"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor3.setOnClickListener {
            selectColor = "#BC1B02"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor4.setOnClickListener {
            selectColor = "#2196F3"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(R.drawable.ic_done)
            binding.layoutMiscellaneous.imageColor5.setImageResource(0)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.viewColor5.setOnClickListener {
            selectColor = "#FF000000"
            binding.layoutMiscellaneous.imageColor1.setImageResource(0)
            binding.layoutMiscellaneous.imageColor2.setImageResource(0)
            binding.layoutMiscellaneous.imageColor3.setImageResource(0)
            binding.layoutMiscellaneous.imageColor4.setImageResource(0)
            binding.layoutMiscellaneous.imageColor5.setImageResource(R.drawable.ic_done)
            setSubtitleColor()
        }
        binding.layoutMiscellaneous.layoutAddImage.setOnClickListener {
            Toast.makeText(this, "onclick", Toast.LENGTH_SHORT).show()
            launcher.launch(
                ImagePicker.with(this)
                    .galleryOnly()
                    .createIntent()
            )
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                //toán tử !! tránh kiểm tra null khi biết chắc chắn biến không null
                // Use the uri to load the image
                var inputStream: InputStream = contentResolver.openInputStream(uri)!!
                var bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                binding.imageNote.setImageBitmap(bitmap)
                binding.imageNote.visibility = View.VISIBLE
                selectImage = getPathFromUri(uri)
            }
        }

    private fun getPathFromUri(uriContent: Uri): String {
        var filePath: String
        var cursor: Cursor? = contentResolver.query(uriContent, null, null, null, null)
        if (cursor == null) {
            filePath = uriContent.path.toString()
        } else {
            cursor.moveToFirst()
            var index: Int = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }


    private fun setSubtitleColor() {
        var gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(Color.parseColor(selectColor))
        binding.viewSubtitle.background = gradientDrawable
    }
}