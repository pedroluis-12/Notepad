package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState

internal interface ListUseCase {
    suspend fun getNotes(): ListGetUseCaseState
    suspend fun deleteNote(index: Int, id: String?): ListDeleteUseCaseState
}
