package com.pedroluis.projects.notepad.features.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCase
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListDeleteViewState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val listUseCase: ListUseCase
) : ViewModel() {

    private val _listGetResult = MutableStateFlow<ListGetViewState>(ListGetViewState.Idle)
    val listGetResult: StateFlow<ListGetViewState> = _listGetResult.asStateFlow()

    private val _listDeleteResult = MutableStateFlow<ListDeleteViewState>(ListDeleteViewState.Idle)
    val listDeleteResult: StateFlow<ListDeleteViewState> = _listDeleteResult.asStateFlow()

    fun getNotes() {
        viewModelScope.launch {
            val result = listUseCase.getNotes()
            handleListGetResult(result)
        }
    }

    fun deleteNote(index: Int, id: String?) {
        viewModelScope.launch {
            val result = listUseCase.deleteNote(index, id)
            handleListDeleteResult(result)
        }
    }

    private fun handleListGetResult(result: ListGetUseCaseState) {
        _listGetResult.value = when (result) {
            is ListGetUseCaseState.Success ->
                ListGetViewState.DisplaySuccess(result.notes)

            is ListGetUseCaseState.EmptyList ->
                ListGetViewState.DisplayEmptyList
        }
    }

    private fun handleListDeleteResult(result: ListDeleteUseCaseState) {
        _listDeleteResult.value = when (result) {
            is ListDeleteUseCaseState.DeleteSuccess ->
                ListDeleteViewState.DisplayDeleteSuccess(result.index)
            is ListDeleteUseCaseState.Error ->
                ListDeleteViewState.DisplayError
        }
    }
}
