package com.pedroluis.projects.notepad.features.list.view.imperative.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.pedroluis.projects.notepad.databinding.NotepadListViewholderBinding

internal class ListNoteViewHolder(
    private val binding: NotepadListViewholderBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String, description: String) {
        binding.apply {
            titleNote.text = title
            descriptionNote.text = description
        }
    }
}