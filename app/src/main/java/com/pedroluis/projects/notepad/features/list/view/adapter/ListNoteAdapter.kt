package com.pedroluis.projects.notepad.features.list.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.databinding.NotepadListViewholderBinding
import com.pedroluis.projects.notepad.features.list.view.adapter.viewholder.ListNoteViewHolder

internal class ListNoteAdapter(
    private val listNotes: MutableList<NotepadModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
       ListNoteViewHolder(
           NotepadListViewholderBinding.inflate(
               LayoutInflater.from(parent.context), parent, false)
       )

    override fun getItemCount(): Int = listNotes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listNotes[position]
        (holder as ListNoteViewHolder).bind(item.id, item.title, item.description)
    }

    fun getNoteAt(position: Int): NotepadModel {
        return listNotes[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAddList(list: List<NotepadModel>) {
        listNotes.clear()
        listNotes.addAll(list)
        notifyDataSetChanged()
    }

    fun removeList(position: Int) {
        listNotes.removeAt(position)
        notifyItemRemoved(position)
    }
}