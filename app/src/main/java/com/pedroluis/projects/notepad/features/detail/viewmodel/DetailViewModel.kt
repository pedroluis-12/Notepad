package com.pedroluis.projects.notepad.features.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCase
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import com.pedroluis.projects.notepad.features.detail.viewmodel.state.DetailViewState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val detailUseCase: DetailUseCase
): ViewModel() {

    private val _detailResult = MutableSharedFlow<DetailViewState>()
    val dataResult: SharedFlow<DetailViewState> = _detailResult

    fun saveNote(index: Int? = null, title: String, description: String) {
        viewModelScope.launch {
            detailUseCase.saveNote(index, title, description).collect { result ->
                handleDetailResult(result)
            }
        }
    }

    private suspend fun handleDetailResult(result: DetailUseCaseState) {
        when (result) {
            DetailUseCaseState.Success -> {
                _detailResult.emit(DetailViewState.DisplaySuccess)
            }
            DetailUseCaseState.ErrorTitle -> {
                _detailResult.emit(DetailViewState.DisplayErrorTitle)
            }
            DetailUseCaseState.ErrorDescription -> {
                _detailResult.emit(DetailViewState.DisplayErrorDescription)
            }
            DetailUseCaseState.ErrorGeneral -> {
                _detailResult.emit(DetailViewState.DisplayErrorGeneral)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
