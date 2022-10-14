package com.example.notesapp.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ItemContainerBinding
import com.example.notesapp.utilities.NoteClickListeners

class NoteAdapter(
    val context: Context,
    private val noteClickListeners: NoteClickListeners

) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(), NoteClickListeners {

    private var differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.subtitle == newItem.subtitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder) {
            with(differ.currentList[position]) {
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
                }
                binding.layoutNote.setOnClickListener {
                    noteClickListeners.noteClickUpDate(differ.currentList[position], position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NoteViewHolder(var binding: ItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun noteClickUpDate(note: Note, position: Int) {

    }
}