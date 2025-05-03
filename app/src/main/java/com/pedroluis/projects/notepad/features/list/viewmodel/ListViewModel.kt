package com.pedroluis.projects.notepad.features.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCase
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListDeleteViewState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


internal class ListViewModel(
    private val listUseCase: ListUseCase
) : ViewModel() {

    private val _listGetResult = MutableSharedFlow<ListGetViewState>()
    val listGetResult: SharedFlow<ListGetViewState> = _listGetResult.asSharedFlow()

    private val _listDeleteResult = MutableSharedFlow<ListDeleteViewState>()
    val listDeleteResult: SharedFlow<ListDeleteViewState> = _listDeleteResult

    fun getNotes() {
        viewModelScope.launch {
            val result = listUseCase.getNotes()
            handleListGetResult(result)
        }
    }

    fun deleteNote(index: Int) {
        viewModelScope.launch {
            val result = listUseCase.deleteNote(index)
            handleListDeleteResult(result)
        }
    }

    private suspend fun handleListGetResult(result: ListGetUseCaseState) {
        when (result) {
            is ListGetUseCaseState.Success -> {
                _listGetResult.emit(ListGetViewState.DisplaySuccess(result.notes))
            }

            is ListGetUseCaseState.EmptyList -> {
                _listGetResult.emit(ListGetViewState.DisplayEmptyList)
            }
        }
    }

    private suspend fun handleListDeleteResult(result: ListDeleteUseCaseState) {
        when (result) {
            ListDeleteUseCaseState.Success -> {
                _listDeleteResult.emit(ListDeleteViewState.DisplaySuccess)
            }
            ListDeleteUseCaseState.Error -> {
                _listDeleteResult.emit(ListDeleteViewState.DisplayError)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
