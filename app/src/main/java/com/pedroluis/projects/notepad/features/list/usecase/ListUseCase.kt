package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState

internal interface ListUseCase {
    fun getNotes(): ListGetUseCaseState
    fun deleteNote(index: Int): ListDeleteUseCaseState
}
