package com.example.notesapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val noteTitle:String,
    val noteBody:String
):Parcelable
//parcelable is an interface which can be implemented to pass the class in an intent