package com.example.notesapplication.repository

import androidx.room.Query
import com.example.notesapplication.database.NoteDatabase
import com.example.notesapplication.model.Note

class NoteRepository(private val db:NoteDatabase) {
    //Suspend function is a function that could be started, paused, and resume. One of the most important points to
    // remember about the suspend functions is that they are only allowed to be called from a coroutine or another
    // suspend function.
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)

}