package com.pedroluis.projects.notepad.features.list.viewmodel.state

import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal sealed class ListGetViewState {
    data object Idle : ListGetViewState()
    data class DisplaySuccess(val notes: List<NotepadModel>) : ListGetViewState()
    data object DisplayEmptyList : ListGetViewState()
}

internal sealed class ListDeleteViewState {
    data object Idle : ListDeleteViewState()
    data class DisplayDeleteSuccess(val index: Int) : ListDeleteViewState()
    data object DisplayError : ListDeleteViewState()
}
