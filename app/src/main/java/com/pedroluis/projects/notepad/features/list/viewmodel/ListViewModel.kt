package com.pedroluis.projects.notepad.features.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.features.list.usecase.ListUseCase
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListDeleteViewState
import com.pedroluis.projects.notepad.features.list.viewmodel.state.ListGetViewState


internal class ListViewModel(
    private val listUseCase: ListUseCase
) : ViewModel() {

    private val _listGetResult = MutableLiveData<ListGetViewState>()
    val listGetResult: LiveData<ListGetViewState> = _listGetResult

    private val _listDeleteResult = MutableLiveData<ListDeleteViewState>()
    val listDeleteResult: LiveData<ListDeleteViewState> = _listDeleteResult

    fun getNotes() {
        val result = listUseCase.getNotes()
        handleListGetResult(result)
    }

    fun deleteNote(index: Int, id: String?) {
        val result = listUseCase.deleteNote(index, id)
        handleListDeleteResult(result)
    }

    private fun handleListGetResult(result: ListGetUseCaseState) {
        when (result) {
            is ListGetUseCaseState.Success ->
                _listGetResult.value = ListGetViewState.DisplaySuccess(result.notes)

            is ListGetUseCaseState.EmptyList ->
                _listGetResult.value = ListGetViewState.DisplayEmptyList
        }
    }

    private fun handleListDeleteResult(result: ListDeleteUseCaseState) {
        when (result) {
            is ListDeleteUseCaseState.DeleteSuccess ->
                _listDeleteResult.value = ListDeleteViewState.DisplayDeleteSuccess(result.index)
            is ListDeleteUseCaseState.DeleteLastItem ->
                _listDeleteResult.value = ListDeleteViewState.DisplayDeleteLastItem
            is ListDeleteUseCaseState.Error ->
                _listDeleteResult.value = ListDeleteViewState.DisplayError
        }
    }
}
