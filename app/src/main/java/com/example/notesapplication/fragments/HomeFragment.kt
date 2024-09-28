package com.example.notesapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplication.MainActivity
import com.example.notesapplication.R
import com.example.notesapplication.adapter.NoteAdapter
import com.example.notesapplication.databinding.FragmentHomeBinding
import com.example.notesapplication.model.Note
import com.example.notesapplication.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding:FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.addMenuProvider(this)
          //setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).notesViewModel

        setUpRecyclerView()
        binding.fabAddNote.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()

         binding.recyclerView.apply {
             layoutManager = StaggeredGridLayoutManager(
                 2,
                 StaggeredGridLayoutManager.VERTICAL
             )
             setHasFixedSize(true)
             adapter = noteAdapter
         }
        activity?.let{
            noteViewModel.getAllNotes().observe(
                viewLifecycleOwner,{
                    note->noteAdapter.differ.submitList(note)
                    updateUI(note)
                }
            )
        }
    }

    private fun updateUI(note: List<Note>?) {
        if (note != null) {
            if(note.isNotEmpty())
            {
                binding.cardView.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
            else{
                binding.recyclerView.visibility = View.GONE
                binding.cardView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //searchNote(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!=null){
            searchNote(newText)
        }
        return true
    }
    private fun searchNote(query: String?)
    {
        val searchQuery = "%$query"
        noteViewModel.searchNote(searchQuery).observe(
            this,
            {list -> noteAdapter.differ.submitList(list)}
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}