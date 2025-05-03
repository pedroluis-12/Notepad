package com.pedroluis.projects.notepad.features.list.usecase

import com.pedroluis.projects.notepad.features.list.repository.ListRepository
import com.pedroluis.projects.notepad.features.list.usecase.state.ListDeleteUseCaseState
import com.pedroluis.projects.notepad.features.list.usecase.state.ListGetUseCaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ListUseCaseImpl(
    private val repository: ListRepository
) : ListUseCase {

    override fun getNotes(): ListGetUseCaseState {
        val listData = repository.getNotes()
        return if (listData.isEmpty()) {
            ListGetUseCaseState.EmptyList
        } else {
            ListGetUseCaseState.Success(listData)
        }
    }

    override fun deleteNote(index: Int): ListDeleteUseCaseState {
        runCatching {
            repository.deleteNote(index)
            return ListDeleteUseCaseState.Success
        }.onFailure {
            return ListDeleteUseCaseState.Error
        }.also {
            return ListDeleteUseCaseState.Error
        }
    }
}
