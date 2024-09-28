package com.example.notesapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.databinding.NoteLayoutBinding
import com.example.notesapplication.fragments.HomeFragmentDirections
import com.example.notesapplication.model.Note
import java.util.Random

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
    //DiffUtil is a utility class that calculates the difference between two lists and outputs a list of update
    // operations that converts the first list into the second one.DiffUtil has a callback class called DiffUtil.callback
    // that computes the variation between two lists. It implements the following methods.
    private val differCallBack = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id && oldItem.noteBody == newItem.noteBody && oldItem.noteTitle == newItem.noteTitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle
        holder.itemBinding.tvNoteBody.text = currentNote.noteBody
        val random = Random()
        val color = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256))
        holder.itemBinding.ibColor.setBackgroundColor(color)
        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}