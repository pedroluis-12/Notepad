package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.commons.model.NotepadModel
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

    override fun deleteNote(index:Int, item: NotepadModel?): ListDeleteUseCaseState {
        return if (item != null) {
            deleteItem(index, item)
        } else {
            ListDeleteUseCaseState.Error
        }
    }

    private fun deleteItem(index: Int, item: NotepadModel): ListDeleteUseCaseState {
        repository.deleteNote(item)

        val listNotes = repository.getNotes()
        return if (listNotes.isEmpty()) {
            ListDeleteUseCaseState.DeleteLastItem
        } else {
            ListDeleteUseCaseState.DeleteSuccess(index)
        }

    }
}
