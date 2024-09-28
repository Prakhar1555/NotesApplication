package com.example.notesapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapplication.model.Note
import com.example.notesapplication.repository.NoteRepository
import kotlinx.coroutines.launch

//Application class represents the base class for maintaining global application state
class NoteViewModel(app:Application,private val noteRepository:NoteRepository):
AndroidViewModel(app){
    fun addNote(note:Note) = viewModelScope.launch {
        noteRepository.insertNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }
    fun getAllNotes() = noteRepository.getAllNotes()
    fun searchNote(query:String?) = noteRepository.searchNote(query)
}