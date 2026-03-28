package com.pedroluis.projects.notepad.features.detail.usecase

import com.pedroluis.projects.notepad.features.detail.repository.DetailRepository
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState

internal class DetailUseCaseImpl(
    private val repository: DetailRepository
) : DetailUseCase {

    override suspend fun saveNote(index: String?, title: String, description: String) = when {
        title.trim().isEmpty() && description.trim().isEmpty() ->
            DetailUseCaseState.ErrorGeneral

        title.trim().isEmpty() ->
            DetailUseCaseState.ErrorTitle

        description.trim().isEmpty() ->
            DetailUseCaseState.ErrorDescription

        else -> setupSaveMode(index, title, description)
    }

    private suspend fun setupSaveMode(
        id: String?, title: String, description: String
    ): DetailUseCaseState.Success {
        if (id != null) {
            repository.editNote(id, title, description)
        } else {
            repository.saveNote(title, description)
        }
        return DetailUseCaseState.Success
    }
}
