package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import kotlinx.coroutines.flow.Flow

internal interface ListUseCase {
    fun getNotes(): ListGetUseCaseState
    fun deleteNote(index:Int, item: NotepadModel?): ListDeleteUseCaseState
}
