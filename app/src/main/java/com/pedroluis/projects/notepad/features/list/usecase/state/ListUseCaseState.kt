package com.pedroluis.projects.notepad.features.list.usecase.state

import com.pedroluis.projects.notepad.commons.model.NotepadModel

sealed class ListGetUseCaseState {
    data class Success(val notes: List<NotepadModel>) : ListGetUseCaseState()
    data object EmptyList : ListGetUseCaseState()
}

sealed class ListDeleteUseCaseState {
    data object Success : ListDeleteUseCaseState()
    data object Error : ListDeleteUseCaseState()
}