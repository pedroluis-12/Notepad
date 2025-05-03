package com.pedroluis.projects.notepad.features.list.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedroluis.projects.notepad.features.list.repository.ListRepositoryImpl
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCaseImpl
import com.pedroluis.projects.notepad.features.list.viewmodel.ListViewModel

@Suppress("UNCHECKED_CAST")
internal class ListViewModelFactory: ViewModelProvider.Factory {
    private val repository = ListRepositoryImpl()
    private val listUseCase = ListUseCaseImpl(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListViewModel(listUseCase) as T
}
