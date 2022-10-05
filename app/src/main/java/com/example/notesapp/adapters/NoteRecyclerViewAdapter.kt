package com.example.notesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.data.Note
import com.example.notesapp.databinding.ItemContainerBinding

class NoteRecyclerViewAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickDelete: NoteClickDeleteInterface

) : RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder>() {

    interface NoteClickInterface {
        fun onNoteClick(note: Note)
    }

    interface NoteClickDeleteInterface {
        fun onNoteClickDelete(note: Note)
    }

    private var allNotes = ArrayList<Note>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder) {
            with(allNotes[position]) {
//                binding.textTitle.setText()
//                binding.textDateTime.text
            }
        }
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    inner class NoteViewHolder(var binding: ItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root)
}