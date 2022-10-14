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
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.R
import com.example.notesapp.data.Note
import com.example.notesapp.database.NoteDataBase
import com.example.notesapp.databinding.ActivityCreateNotesBinding
import com.example.notesapp.repositories.NoteRepository
import com.example.notesapp.utilities.Coroutines
import com.example.notesapp.utilities.CreateDialog
import com.example.notesapp.viewmodels.CreateViewModel
import com.example.notesapp.viewmodels.MyViewModelFactory
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class CreateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNotesBinding
    private lateinit var createViewModel: CreateViewModel
    private val formatter =
        SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(
            Date()
        )
    private var selectColor = "#333333"
    private var selectImage = ""
    private lateinit var alReadyNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
        binding.textDateTime.text = formatter
        binding.imgSave.setOnClickListener {
            insertDataToDataBase()
        }
        initMiscellaneous()
        setViewOrUpdateNote()
        binding.imageDeleteIMG.setOnClickListener {
            deleteImage()
        }
        binding.imageDeleteURL.setOnClickListener {
            deleteUrl()
        }
        if (intent.getBooleanExtra("isAddUrl",false)){
            binding.textWebURL.text = intent.getStringExtra("url")
            binding.layoutURL.visibility = View.VISIBLE
        }
        showAddURLDialog()
        showDeleteNote()
    }

    private fun deleteUrl() {
        binding.layoutURL.visibility = View.GONE
        binding.textWebURL.text = ""
        binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(alReadyNote.imagePath))
        Snackbar.make(binding.root, "Delete URL Successfully", Snackbar.LENGTH_LONG).show()
//                .setAction(getString(R.string.Retry_delete_URL)) {
//                    binding.layoutURL.visibility = View.VISIBLE
//                    binding.textWebURL.text = alReadyNote.webLink
//                }.show()
    }

    private fun deleteImage() {
        binding.imageNote.visibility = View.GONE
        binding.imageDeleteIMG.visibility = View.GONE
        binding.imageNote.setImageBitmap(null)
        selectImage = ""
        Snackbar.make(binding.root, "Delete Image Successfully", Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry_delete)) {
                binding.imageNote.visibility = View.VISIBLE
                binding.imageDeleteIMG.visibility = View.VISIBLE
                binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(alReadyNote.imagePath))
                selectImage = alReadyNote.imagePath
            }.show()
    }

    private fun showDeleteNote() {
        if (intent.getBooleanExtra("isViewOrUpdate", false)) {
            binding.layoutMiscellaneous.layoutDelete.visibility = View.VISIBLE
            binding.layoutMiscellaneous.layoutDelete.setOnClickListener {
                CreateDialog(R.layout.layout_delete, this, 0).also { dialog ->
                    dialog.findViewById<View>(R.id.tvDeleteNote).setOnClickListener {
                        createViewModel.deleteNote(alReadyNote)
                        dialog.dismiss()
                        finish()
                    }
                    dialog.findViewById<View>(R.id.tvCancelDelete).setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    private fun setViewOrUpdateNote() {
        if (intent.getBooleanExtra("isViewOrUpdate", false)) {
            alReadyNote = intent.getParcelableExtra("note")!!
            binding.edNoteTitle.setText(alReadyNote.title)
            binding.edNoteMain.setText(alReadyNote.noteText)
            if (alReadyNote.imagePath.isNotEmpty() && alReadyNote.imagePath != null) {
                binding.imageNote.setImageBitmap(BitmapFactory.decodeFile(alReadyNote.imagePath))
                selectImage = alReadyNote.imagePath
                binding.imageNote.visibility = View.VISIBLE
                binding.imageDeleteIMG.visibility = View.VISIBLE
            }
            if (alReadyNote.webLink.isEmpty() && alReadyNote.webLink != null) {
                binding.textWebURL.text = alReadyNote.webLink
                binding.layoutURL.visibility = View.GONE
            } else {
                binding.textWebURL.text = alReadyNote.webLink
                binding.layoutURL.visibility = View.VISIBLE
            }
            binding.edNoteSubTitle.setText(alReadyNote.subtitle)
            selectColor = alReadyNote.color
            setSubtitleColor()
        }
    }

    private fun insertDataToDataBase() {
        val title = binding.edNoteTitle.text.toString()
        val subTitle = binding.edNoteSubTitle.text.toString()
        val noteMain = binding.edNoteMain.text.toString()
        val linkUrl = binding.textWebURL.text.toString()

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
                linkUrl
            )
            if (intent.getBooleanExtra("isViewOrUpdate", false)) {
                note.id = alReadyNote.id
                note.imagePath = selectImage
                createViewModel.updateNote(note)
                Snackbar.make(binding.root, "Successfully Update", Snackbar.LENGTH_LONG).show()
            } else {
                createViewModel.addNote(note)
                Snackbar.make(binding.root, "Successfully Added", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(binding.root, "Successfully Fail", Snackbar.LENGTH_LONG).show()
        }
        Coroutines.io {
            runCatching {
                delay(2500)
                finish()
            }
        }
    }

    private fun checkNote(
        title: String,
        subTitle: String,
        noteMain: String,
    ): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(subTitle) && TextUtils.isEmpty(
            noteMain
        ))
    }

    private fun initMiscellaneous() {
        val from = BottomSheetBehavior.from(binding.layoutMiscellaneous.layoutMiscellaneous)
        binding.layoutMiscellaneous.layoutMiscellaneous.setOnClickListener {
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
                val inputStream: InputStream = contentResolver.openInputStream(uri)!!
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                binding.imageNote.setImageBitmap(bitmap)
                binding.imageNote.visibility = View.VISIBLE
                selectImage = getPathFromUri(uri)
            }
        }

    private fun getPathFromUri(uriContent: Uri): String {
        val filePath: String
        val cursor: Cursor? = contentResolver.query(uriContent, null, null, null, null)
        if (cursor == null) {
            filePath = uriContent.path.toString()
        } else {
            cursor.moveToFirst()
            val index: Int = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }


    private fun setSubtitleColor() {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(Color.parseColor(selectColor))
        binding.viewSubtitle.background = gradientDrawable
    }

    private fun showAddURLDialog() {
        binding.layoutMiscellaneous.layoutAddUrl.setOnClickListener {
            CreateDialog(R.layout.layout_add_url, this, 0).also { dialog ->
                val urlink = dialog.findViewById<EditText>(R.id.edAddURL)
                if (intent.getBooleanExtra("isViewOrUpdate", false)) {
                    dialog.findViewById<EditText>(R.id.edAddURL).setText(alReadyNote.webLink)
                }
                urlink.requestFocus()
                dialog.findViewById<View>(R.id.textAdd).setOnClickListener {
                    if (urlink.text.isEmpty() && urlink != null) {
                        Snackbar.make(binding.root, "No URL", Snackbar.LENGTH_LONG).show()
                        dialog.dismiss()
                    } else {
                        binding.textWebURL.text = urlink.text.toString()
                        binding.layoutURL.visibility = View.VISIBLE
                        dialog.dismiss()
                    }
                }
                dialog.findViewById<View>(R.id.textCancel).setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
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
        createViewModel = ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java)
    }
}