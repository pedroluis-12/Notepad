package com.pedroluis.projects.notepad.features.list.view.imperative.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.databinding.NotepadListFragmentBinding
import com.pedroluis.projects.notepad.features.list.view.imperative.adapter.ListNoteAdapter

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

        binding.fabAddNote.setOnClickListener { button ->
            findNavController().navigate(R.id.action_ListFragment_to_DetailFragment)
        }

        val list = listOf(
            NotepadModel("teste", "teste"),
            NotepadModel("teste", "teste"),
            NotepadModel("teste", "teste")
        )

        setupBookListRecycler(list)
    }

    private fun setupBookListRecycler(list: List<NotepadModel>) {
        binding.listNote.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = ListNoteAdapter(list.toMutableList())
            visibility = View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
