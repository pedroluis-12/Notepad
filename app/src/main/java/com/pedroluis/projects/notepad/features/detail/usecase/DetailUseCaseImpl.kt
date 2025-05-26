package com.pedroluis.projects.notepad.features.detail.usecase

import android.app.Application
import com.pedroluis.projects.notepad.features.detail.repository.DetailRepository
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class DetailUseCaseImpl(
    private val application: Application,
    private val repository: DetailRepository
) : DetailUseCase {

    override fun saveNote(index: String?, title: String, description: String) = when {
        title.trim().isEmpty() && description.trim().isEmpty() ->
            DetailUseCaseState.ErrorGeneral

        title.trim().isEmpty() ->
            DetailUseCaseState.ErrorTitle

        description.trim().isEmpty() ->
            DetailUseCaseState.ErrorDescription

        else -> setupSaveMode(index, title, description)
    }

    private fun setupSaveMode(
        id: String?, title: String, description: String
    ): DetailUseCaseState.Success {
        if (id != null) {
            repository.editNote(application, id, title, description)
        } else {
            repository.saveNote(application, title, description)
        }
        return DetailUseCaseState.Success
    }
}
