package com.pedroluis.projects.notepad.features.list.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.commons.UPDATE_LIST
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.databinding.NotepadListFragmentBinding
import com.pedroluis.projects.notepad.features.list.view.adapter.ListNoteAdapter
import com.pedroluis.projects.notepad.features.list.viewmodel.ListViewModel
import com.pedroluis.projects.notepad.features.list.viewmodel.factory.ListViewModelFactory
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListDeleteViewState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val INDEX_ZERO = 0

class ListFragment : Fragment() {

    private var _binding: NotepadListFragmentBinding? = null
    private val binding get() = _binding as NotepadListFragmentBinding

    private var listAdapter: ListNoteAdapter? = null
    private val viewModel by lazy { setViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotepadListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()

        setObserveListGetViewModel()
        setObserveListDeleteViewModel()

        viewModel.getNotes()
    }

    private fun setViewModel(): ListViewModel = ViewModelProvider(
        this, ListViewModelFactory(this.requireActivity().application)
    )[ListViewModel::class.java]

    private fun setObserveListGetViewModel() {
        viewModel.listGetResult.observe(viewLifecycleOwner) { value ->
            when (value) {
                is ListGetViewState.DisplaySuccess -> setListSuccess(value.notes)
                is ListGetViewState.DisplayEmptyList -> setListEmpty()
            }
        }
    }

    private fun setObserveListDeleteViewModel() {
        viewModel.listDeleteResult.observe(viewLifecycleOwner) { value ->
            when (value) {
                is ListDeleteViewState.DisplayDeleteSuccess ->
                   viewModel.getNotes()
                is ListDeleteViewState.DisplayError ->
                    setItemListDeletedError()
            }
        }
    }

    private fun setViews() {
        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_DetailFragment)
        }

        listAdapter = ListNoteAdapter(mutableListOf())
        binding.listNote.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = listAdapter
        }
        ItemTouchHelper(setDeleteListItem()).attachToRecyclerView(binding.listNote)
    }

    private fun setListEmpty() {
        binding.apply {
            emptyListMessage.visibility = View.VISIBLE
            listNote.visibility = View.GONE
        }
    }

    private fun setListSuccess(notes: List<NotepadModel>) {
        listAdapter?.setAddList(notes)
        binding.apply {
            emptyListMessage.visibility = View.GONE
            listNote.visibility = View.VISIBLE
        }
    }

    private fun setItemListDeletedError() {
        Snackbar.make(
            binding.root,
            getString(R.string.notepad_list_deleted_error),
            Snackbar.LENGTH_LONG
        ).show()
        viewModel.getNotes()
    }

    private fun setDeleteListItem() = object : ItemTouchHelper.SimpleCallback(
        INDEX_ZERO, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val noteToDelete = listAdapter?.getNoteAt(position)

           viewModel.deleteNote(position, noteToDelete?.id)
            Snackbar.make(
                binding.root,
                getString(R.string.notepad_list_deleted),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
