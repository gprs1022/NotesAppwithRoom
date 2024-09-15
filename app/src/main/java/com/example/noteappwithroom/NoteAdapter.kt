package com.example.noteappwithroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(
    private var notes: List<Note>,
    private val deleteNote: (Note) -> Unit,
    private val updateNote: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Inflate the custom layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item , parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)


        holder.deleteButton.setOnClickListener {
            deleteNote.invoke(note)
        }

        holder.updateButton.setOnClickListener {
            updateNote.invoke(note)
        }
    }

    override fun getItemCount(): Int = notes.size
    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged() // Notify the adapter that data has changed
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize the views with the correct IDs
        val noteTextView: TextView = itemView.findViewById(R.id.note_text)
        val deleteButton: Button = itemView.findViewById(R.id.delete_button)
        val updateButton: Button = itemView.findViewById(R.id.update_button)


        fun bind(note: Note) {
            noteTextView.text = note.text // Display the note text
        }

    }


}
