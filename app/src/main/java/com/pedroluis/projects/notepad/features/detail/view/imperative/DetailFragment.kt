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
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.databinding.NotepadDetailFragmentBinding
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import com.pedroluis.projects.notepad.features.detail.viewmodel.DetailViewModel
import com.pedroluis.projects.notepad.features.detail.viewmodel.factory.DetailViewModelFactory
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: NotepadDetailFragmentBinding? = null
    private val binding get() = _binding as NotepadDetailFragmentBinding

    private var viewModel: DetailViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotepadDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            validateInputs()
        }

        // Clear error on input change
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

        setViewModel()
    }

    private fun setViewModel() {
        val viewModelFactory = DetailViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        setObserveDetailResult()
    }

    private fun setObserveDetailResult() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel?.dataResult?.collect { result ->
                    when (result) {
                        DetailUseCaseState.Success -> {
                            val title = binding.titleEditText.text.toString().trim()
                            val description = binding.descriptionEditText.text.toString().trim()
                            saveNote(title, description)
                        }

                        is DetailUseCaseState.ErrorGeneral -> {
                            binding.titleInputLayout.isErrorEnabled = true
                            binding.titleInputLayout.error = getString(R.string.notepad_detail_title_cannot_be_empty)
                            binding.descriptionInputLayout.isErrorEnabled = true
                            binding.descriptionInputLayout.error = getString(R.string.notepad_detail_description_cannot_be_empty)
                        }
                        is DetailUseCaseState.ErrorTitle -> {
                            binding.titleInputLayout.isErrorEnabled = true
                            binding.titleInputLayout.error = getString(R.string.notepad_detail_title_cannot_be_empty)
                        }
                        is DetailUseCaseState.ErrorDescription -> {
                            binding.descriptionInputLayout.isErrorEnabled = true
                            binding.descriptionInputLayout.error = getString(R.string.notepad_detail_description_cannot_be_empty)
                        }
                    }
                }
            }
        }
    }

    private fun validateInputs() {
        val title = binding.titleEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()
        viewModel?.saveNote(title, description)
    }

    private fun saveNote(title: String, description: String) {
        // TODO: Implement your logic to save the note here.
        // For example, you might want to:
        // 1. Create a data object for your note.
        // 2. Save it to a database (Room, etc.).
        // 3. Send it to a server.
        // 4. Navigate back to the list.

        println("Note saved: Title - $title, Description - $description")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.cancel()
        _binding = null
    }
}