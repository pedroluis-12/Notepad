package com.pedroluis.projects.notepad.features.list.view.adapter.viewholder

import android.os.Bundle
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.commons.DESCRIPTION_FROM_LIST
import com.pedroluis.projects.notepad.commons.ID_FROM_LIST
import com.pedroluis.projects.notepad.commons.TITLE_FROM_LIST
import com.pedroluis.projects.notepad.databinding.NotepadListViewholderBinding

internal class ListNoteViewHolder(
    private val binding: NotepadListViewholderBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(id: String, title: String, description: String) {
        binding.apply {
            titleNote.text = title
            descriptionNote.text = description

            val bundle = Bundle().apply {
                putString(ID_FROM_LIST, id)
                putString(TITLE_FROM_LIST, title)
                putString(DESCRIPTION_FROM_LIST, description)
            }

            this.root.setOnClickListener {
                findNavController(
                    itemView.findFragment()
                ).navigate(R.id.action_ListFragment_to_DetailFragment, bundle)
            }
        }
    }
}
