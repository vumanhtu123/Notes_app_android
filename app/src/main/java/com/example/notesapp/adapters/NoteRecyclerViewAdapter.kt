package com.example.notesapp.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ItemContainerBinding
import com.example.notesapp.utilities.NoteClickListeners

class NoteRecyclerViewAdapter(
    val context: Context,
    private val allNotesList: List<Note>,
    private val noteClickListeners: NoteClickListeners

) : RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder>() , NoteClickListeners{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder) {
            with(allNotesList[position]) {
                binding.textTitle.text = title
                binding.textDateTime.text = datetime
                if (subtitle.isEmpty()) {
                    binding.textSubTitle.visibility = View.GONE
                } else {
                    binding.textSubTitle.text = subtitle
                }

                val gradientDrawable = GradientDrawable()
                binding.layoutNote.background = gradientDrawable
                if (color != null) {
                    gradientDrawable.setColor(Color.parseColor(color))

                } else {
                    gradientDrawable.setColor(Color.parseColor("#333333"))
                }
                if (imagePath != null) {
                    binding.imageViewNote.setImageBitmap(BitmapFactory.decodeFile(imagePath))
                    binding.imageViewNote.visibility = View.VISIBLE
                } else {
                    binding.imageViewNote.visibility = View.GONE
                }
                binding.layoutNote.setOnClickListener {
                    noteClickListeners.noteClickUpDate(allNotesList[position], position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return allNotesList.size
    }

    inner class NoteViewHolder(var binding: ItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun noteClickUpDate(note: Note, position: Int) {

    }
}