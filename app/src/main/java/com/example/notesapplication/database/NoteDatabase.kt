package com.example.notesapplication.database

import android.content.Context
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapplication.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun getNoteDao() : NoteDAO

    //companion object is used for returning only single object from this class inorder to  maintain singleton pattern
    companion object{
        //Volatile is used so that changes made to the variable are immediately visible
        @Volatile
        private var instance:NoteDatabase?=null
        //Locks are used for controlling access to shared preferences.Commonly, only one thread at a time can acquire the lock
        private var LOCK = Any()

        //In Kotlin, operator functions are special functions that can be used to implement custom versions of predefined operators. They are denoted by the keyword operator before their name
        //invoke function is used to call a function for eg. fun.invoke()
        operator fun invoke(context: Context) = instance?:
        //synchronized is used to control access to a shared resource by multiple threads, ensuring that only one thread can execute the synchronized block at a time.
        synchronized(LOCK){
            instance?:
            createDatabase(context).also{
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            NoteDatabase::class.java,
            "room_db").build()
    }
}