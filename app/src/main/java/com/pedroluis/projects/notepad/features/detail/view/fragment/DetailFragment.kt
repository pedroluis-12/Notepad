package com.pedroluis.projects.notepad.features.detail.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pedroluis.projects.notepad.R
import com.pedroluis.projects.notepad.commons.ID_FROM_LIST
import com.pedroluis.projects.notepad.commons.TITLE_FROM_LIST
import com.pedroluis.projects.notepad.commons.UPDATE_LIST
import com.pedroluis.projects.notepad.commons.DESCRIPTION_FROM_LIST
import com.pedroluis.projects.notepad.databinding.NotepadDetailFragmentBinding
import com.pedroluis.projects.notepad.features.detail.viewmodel.DetailViewModel
import com.pedroluis.projects.notepad.features.detail.viewmodel.factory.DetailViewModelFactory
import com.pedroluis.projects.notepad.features.detail.viewmodel.state.DetailViewState

class DetailFragment : Fragment() {

    private var _binding: NotepadDetailFragmentBinding? = null
    private val binding get() = _binding as NotepadDetailFragmentBinding

    private val viewModel: DetailViewModel by lazy { setViewModel() }
    private var idNote: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = NotepadDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setListeners()
        setObserveDetailResult()
    }

    private fun setViews() {
        val id = arguments?.getString(ID_FROM_LIST)
        val title = arguments?.getString(TITLE_FROM_LIST)
        val description = arguments?.getString(DESCRIPTION_FROM_LIST)

        if (id != null && title != null && description != null) {
            idNote = id
            binding.titleEditText.setText(title)
            binding.descriptionEditText.setText(description)
        }
    }

    private fun setListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.saveNote(
                id = idNote,
                title = binding.titleEditText.text.toString().trim(),
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

    private fun setViewModel() = ViewModelProvider(
        this, DetailViewModelFactory(this.requireActivity().application)
    )[DetailViewModel::class.java]

    private fun setObserveDetailResult() {
        viewModel.dataResult.observe(viewLifecycleOwner) { value ->
            when (value) {
                is DetailViewState.DisplaySuccess -> setupSuccess()
                is DetailViewState.DisplayErrorGeneral -> setupErrorGeneral()
                is DetailViewState.DisplayErrorTitle -> setupErrorTitle()
                is DetailViewState.DisplayErrorDescription -> setupErrorDescription()
            }
        }
    }

    private fun setupErrorGeneral() {
        setupErrorTitle()
        setupErrorDescription()
    }

    private fun setupErrorDescription() {
        binding.descriptionInputLayout.isErrorEnabled = true
        binding.descriptionInputLayout.error = getString(
            R.string.notepad_detail_description_cannot_be_empty
        )
    }

    private fun setupErrorTitle() {
        binding.titleInputLayout.isErrorEnabled = true
        binding.titleInputLayout.error = getString(R.string.notepad_detail_title_cannot_be_empty)
    }

    private fun setupSuccess() {
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set(
            UPDATE_LIST, true
        )
        navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
