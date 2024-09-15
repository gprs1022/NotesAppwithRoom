package com.example.noteappwithroom

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        val addNoteButton = view.findViewById<Button>(R.id.add_note_button)
        val logoutButton = view.findViewById<Button>(R.id.logout_button)

        // Set up RecyclerView
        noteAdapter = NoteAdapter(emptyList(), { note -> deleteNote(note) }, { note -> updateNote(note) })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = noteAdapter

        // Load notes for the logged-in user
        loadNotesForUser()

        // Handle Add Note button click
        addNoteButton.setOnClickListener {
            (activity as MainActivity).showFragment(AddNoteFregment())
        }

        // Handle Logout button click
        logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadNotesForUser() {
        // Get the current logged-in user's ID from Google Sign-In
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val userId = account?.id ?: ""  // Use Google account ID

        // Store the user ID in SharedPreferences (for future use)
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("user_id", userId).apply()

        // Get all notes for the logged-in user
        val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
        noteDao.getAllNotes(userId).observe(viewLifecycleOwner, Observer { notes ->
            // Update the RecyclerView when notes data changes
            noteAdapter.updateNotes(notes)
        })
    }

    private fun deleteNote(note: Note) {
        // Delete the selected note from the Room database
        lifecycleScope.launch(Dispatchers.IO) {
            val noteDao = NoteDatabase.getDatabase(requireContext()).noteDao()
            noteDao.delete(note)
        }
    }

    private fun updateNote(note: Note) {
        // Navigate to AddNoteFragment with the note to be updated
        val fragment = AddNoteFregment()
        val bundle = Bundle()
        bundle.putInt("note_id", note.id)  // Pass note ID
        bundle.putString("note_text", note.text)  // Pass note text
        fragment.arguments = bundle

        // Show AddNoteFragment to edit the note
        (activity as MainActivity).showFragment(fragment)
    }

    private fun logoutUser() {
        // Clear shared preferences to log the user out
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("is_logged_in", false).apply()

        // Sign out from Google
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut().addOnCompleteListener {
            // Show the login fragment after logout
            (activity as MainActivity).showFragment(LoginFragment())
        }
    }
}
