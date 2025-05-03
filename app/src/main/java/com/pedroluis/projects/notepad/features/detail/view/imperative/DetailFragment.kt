package com.pedroluis.projects.notepad.features.detail.view.imperative

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.databinding.NotepadDetailFragmentBinding
import com.pedroluis.projects.notepad.features.detail.viewmodel.DetailViewModel
import com.pedroluis.projects.notepad.features.detail.viewmodel.factory.DetailViewModelFactory
import com.pedroluis.projects.notepad.features.detail.viewmodel.state.DetailViewState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: NotepadDetailFragmentBinding? = null
    private val binding get() = _binding as NotepadDetailFragmentBinding

    private var viewModel: DetailViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = NotepadDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setViewModel()
    }

    private fun setListeners() {
        binding.saveButton.setOnClickListener {
            viewModel?.saveNote(
                title =  binding.titleEditText.text.toString().trim(),
                description = binding.descriptionEditText.text.toString().trim()
            )
        }

        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.titleInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.descriptionEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.descriptionInputLayout.isErrorEnabled = false
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this, DetailViewModelFactory()
        )[DetailViewModel::class.java]

        setObserveDetailResult()
    }

    private fun setObserveDetailResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.dataResult?.collect { result ->
                    when (result) {
                        is DetailViewState.DisplaySuccess -> setupSuccess()
                        is DetailViewState.DisplayErrorGeneral -> setupErrorGeneral()
                        is DetailViewState.DisplayErrorTitle -> setupErrorTitle()
                        is DetailViewState.DisplayErrorDescription -> setupErrorDescription()
                    }
                }
            }
        }
    }

    private fun setupErrorGeneral() {
        setupErrorTitle()
        setupErrorDescription()
    }

    private fun setupErrorDescription() {
        binding.descriptionInputLayout.isErrorEnabled = true
        binding.descriptionInputLayout.error =
            getString(R.string.notepad_detail_description_cannot_be_empty)
    }

    private fun setupErrorTitle() {
        binding.titleInputLayout.isErrorEnabled = true
        binding.titleInputLayout.error =
            getString(R.string.notepad_detail_title_cannot_be_empty)
    }

    private fun setupSuccess() {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "key", "value that needs to be passed"
        )
        navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
        _binding = null
    }
}