package com.example.notesapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesapplication.model.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Update
    suspend fun updateNote(note:Note)
    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Note" )
    fun getAllNotes():LiveData<List<Note>>
    @Query("SELECT * FROM Note WHERE noteTitle Like :query OR noteBody Like:query")
    fun searchNote(query :String?):LiveData<List<Note>>
}