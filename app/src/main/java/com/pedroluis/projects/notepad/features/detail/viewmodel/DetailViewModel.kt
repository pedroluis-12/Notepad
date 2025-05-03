package com.pedroluis.projects.notepad.features.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCase
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val detailUseCase: DetailUseCase
): ViewModel() {

    private val _detailResult = MutableSharedFlow<DetailUseCaseState>()
    val dataResult: SharedFlow<DetailUseCaseState> = _detailResult

    fun saveNote(title: String, description: String) {
        viewModelScope.launch {
            detailUseCase.saveNote(title, description).collect { result ->
                _detailResult.emit(result)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
