package com.pedroluis.projects.notepad.features.list.view.imperative.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.databinding.NotepadListFragmentBinding
import com.pedroluis.projects.notepad.features.list.view.imperative.adapter.ListNoteAdapter
import com.pedroluis.projects.notepad.features.list.viewmodel.ListViewModel
import com.pedroluis.projects.notepad.features.list.viewmodel.factory.ListViewModelFactory
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListDeleteViewState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

        setObserveDataFromBack()
        setObserveListGetViewModel()
        setObserveListDeleteViewModel()

        viewModel.getNotes()
    }

    private fun setViewModel(): ListViewModel = ViewModelProvider(
            this, ListViewModelFactory()
        )[ListViewModel::class.java]

    private fun setObserveDataFromBack() {
        val navController = findNavController()
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("key", "")
            ?.flowWithLifecycle(this.lifecycle)
            ?.onEach { result ->
                if (result.isNotEmpty()) {
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("key")
                }
            }?.launchIn(lifecycleScope)
    }

    private fun setObserveListGetViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listGetResult.collect { value ->
                    when (value) {
                        is ListGetViewState.DisplaySuccess -> setListSuccess(value.notes)
                        is ListGetViewState.DisplayEmptyList -> setListEmpty()
                    }
                }
            }
        }
    }

    private fun setObserveListDeleteViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listDeleteResult.collect { value ->
                    when (value) {
                        is ListDeleteViewState.DisplaySuccess -> {}
                        is ListDeleteViewState.DisplayError -> {}
                    }
                }
            }
        }
    }

    private fun setViews() {
        binding.fabAddNote.setOnClickListener { button ->
            findNavController().navigate(R.id.action_ListFragment_to_DetailFragment)
        }

        listAdapter = ListNoteAdapter(mutableListOf())
        binding.listNote.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = adapter
        }
    }

    private fun setListEmpty() {
        binding.apply {
            emptyListMessage.visibility = View.VISIBLE
            listNote.visibility = View.GONE
        }
    }

    private fun setListSuccess(notes: List<NotepadModel>) {
        binding.apply {
            emptyListMessage.visibility = View.GONE
            listNote.visibility = View.VISIBLE
        }
        listAdapter?.setAddList(notes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifecycleOwner.lifecycleScope.cancel()
        _binding = null
    }
}
