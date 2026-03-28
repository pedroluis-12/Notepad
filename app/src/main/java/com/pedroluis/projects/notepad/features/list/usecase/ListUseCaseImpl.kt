package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.features.list.repository.ListRepository
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState

internal class ListUseCaseImpl(
    private val repository: ListRepository
) : ListUseCase {

    override fun getNotes(): ListGetUseCaseState {
        val listNotes = repository.getNotes()
        return if (listNotes.isEmpty()) {
            ListGetUseCaseState.EmptyList
        } else {
            ListGetUseCaseState.Success(listNotes)
        }
    }

    override fun deleteNote(index: Int, id: String?): ListDeleteUseCaseState {
        return if (id != null) {
            repository.deleteNote(id)
            ListDeleteUseCaseState.DeleteSuccess(index)
        } else {
            ListDeleteUseCaseState.Error
        }
    }
}
