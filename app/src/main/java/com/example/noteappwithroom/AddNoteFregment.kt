package com.example.noteappwithroom

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteFregment : Fragment() {
    private lateinit var noteTextView: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        noteTextView = view.findViewById(R.id.note_input)
        val saveButton = view.findViewById<Button>(R.id.save_note_button)
        // Check if this is an update or a new note

        saveButton.setOnClickListener {
            saveNote()
        }
        return view
    }
    private fun saveNote() {
        val noteText = noteTextView.text.toString()

        if (noteText.isNotEmpty()) {
            val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("user_id", "") ?: ""

            val newNote = Note(
                text = noteText,
                userId = userId // Associate note with the current user
            )

            lifecycleScope.launch(Dispatchers.IO) {
                NoteDatabase.getDatabase(requireContext()).noteDao().insert(newNote)
                requireActivity().runOnUiThread {
                    (activity as MainActivity).showFragment(NoteListFragment())
                }
            }
        }
    }

}



