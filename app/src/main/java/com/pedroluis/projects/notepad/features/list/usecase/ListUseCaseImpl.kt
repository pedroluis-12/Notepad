package com.pedroluis.projects.notepad.features.list.usecase

import android.app.Application
import com.pedroluis.projects.notepad.commons.model.NotepadModel
import com.pedroluis.projects.notepad.features.list.repository.ListRepository
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState

internal class ListUseCaseImpl(
    private val application: Application,
    private val repository: ListRepository
) : ListUseCase {

    override fun getNotes(): ListGetUseCaseState {
        val listNotes = repository.getNotes(application)
        return if (listNotes.isEmpty()) {
            ListGetUseCaseState.EmptyList
        } else {
            ListGetUseCaseState.Success(listNotes)
        }
    }

    override fun deleteNote(index:Int, id: String?): ListDeleteUseCaseState {
        return if (id != null) {
            repository.deleteNote(application, id)
            ListDeleteUseCaseState.DeleteSuccess(index)
        } else {
            ListDeleteUseCaseState.Error
        }
    }
}
