package com.pedroluis.projects.notepad.features.list.view.imperative.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.databinding.NotepadListFragmentBinding

class ListFragment : Fragment() {

    private var _binding: NotepadListFragmentBinding? = null
    private val binding get() = _binding as NotepadListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotepadListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_DetailFragment)
        }

        binding.fabAddNote.setOnClickListener { button ->
            Snackbar.make(button, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab_add_note).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
