package com.example.notesapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notesapplication.database.NoteDatabase
import com.example.notesapplication.databinding.ActivityMainBinding
import com.example.notesapplication.repository.NoteRepository
import com.example.notesapplication.viewmodel.NoteViewModel
import com.example.notesapplication.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var notesViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application,noteRepository)
        notesViewModel = ViewModelProvider(this,viewModelProviderFactory).get(NoteViewModel::class.java)
    }
}