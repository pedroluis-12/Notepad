package com.pedroluis.projects.notepad.features.detail.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedroluis.projects.notepad.features.detail.repository.DetailRepositoryImpl
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCaseImpl
import com.pedroluis.projects.notepad.features.detail.viewmodel.DetailViewModel

class DetailViewModelFactory: ViewModelProvider.Factory {

    private val repository = DetailRepositoryImpl()
    private val detailUseCase = DetailUseCaseImpl(repository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DetailViewModel(detailUseCase) as T
}
