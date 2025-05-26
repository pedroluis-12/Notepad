package com.pedroluis.projects.notepad.features.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _detailResult = MutableLiveData<DetailViewState>()
    val dataResult: LiveData<DetailViewState> = _detailResult

    fun saveNote(id: String? = null, title: String, description: String) {
        val result = detailUseCase.saveNote(id, title, description)
        handleDetailResult(result)
    }

    private fun handleDetailResult(result: DetailUseCaseState) {
        when (result) {
            DetailUseCaseState.ErrorTitle ->
                _detailResult.value = DetailViewState.DisplayErrorTitle

            DetailUseCaseState.ErrorDescription ->
                _detailResult.value = DetailViewState.DisplayErrorDescription

            DetailUseCaseState.ErrorGeneral ->
                _detailResult.value = DetailViewState.DisplayErrorGeneral

            DetailUseCaseState.Success ->
                _detailResult.value = DetailViewState.DisplaySuccess
        }
    }
}
