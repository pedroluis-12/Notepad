package com.pedroluis.projects.notepad.features.list.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedroluis.projects.notepad.features.list.repository.ListRepositoryImpl
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCaseImpl
import com.pedroluis.projects.notepad.features.list.viewmodel.ListViewModel

@Suppress("UNCHECKED_CAST")
internal class ListViewModelFactory(application: Application): ViewModelProvider.Factory {
    private val repository = ListRepositoryImpl()
    private val listUseCase = ListUseCaseImpl(application, repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ListViewModel(listUseCase) as T
}
