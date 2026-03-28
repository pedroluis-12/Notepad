package com.pedroluis.projects.notepad.features.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.notepad.features.detail.usecase.DetailUseCase
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import com.pedroluis.projects.notepad.features.detail.viewmodel.state.DetailViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val detailUseCase: DetailUseCase
): ViewModel() {

    private val _detailResult = MutableStateFlow<DetailViewState>(DetailViewState.Idle)
    val dataResult: StateFlow<DetailViewState> = _detailResult.asStateFlow()

    fun saveNote(id: String? = null, title: String, description: String) {
        viewModelScope.launch {
            val result = detailUseCase.saveNote(id, title, description)
            handleDetailResult(result)
        }
    }

    private fun handleDetailResult(result: DetailUseCaseState) {
        _detailResult.value = when (result) {
            DetailUseCaseState.ErrorTitle ->
                DetailViewState.DisplayErrorTitle

            DetailUseCaseState.ErrorDescription ->
                DetailViewState.DisplayErrorDescription

            DetailUseCaseState.ErrorGeneral ->
                DetailViewState.DisplayErrorGeneral

            DetailUseCaseState.Success ->
                DetailViewState.DisplaySuccess
        }
    }
}
