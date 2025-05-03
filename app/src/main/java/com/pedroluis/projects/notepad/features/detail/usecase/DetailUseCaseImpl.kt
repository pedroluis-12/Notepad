package com.pedroluis.projects.notepad.features.detail.usecase

import com.pedroluis.projects.notepad.features.detail.repository.DetailRepository
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class DetailUseCaseImpl(
    private val repository: DetailRepository
) : DetailUseCase {

    override fun saveNote(index: Int?, title: String, description: String): Flow<DetailUseCaseState> = flow {
        when {
            title.trim().isEmpty() && description.trim().isEmpty() -> {
                emit(DetailUseCaseState.ErrorGeneral)
                return@flow
            }

            title.trim().isEmpty() -> {
                emit(DetailUseCaseState.ErrorTitle)
                return@flow
            }

            description.trim().isEmpty() -> {
                emit(DetailUseCaseState.ErrorDescription)
                return@flow
            }

            else -> {
                setupSaveMode(index, title, description)
                emit(DetailUseCaseState.Success)
                return@flow
            }
        }
    }

    private fun setupSaveMode(index: Int?, title: String, description: String) {
        if (index != null) {
            repository.editNote(index, title, description)
        } else {
            repository.saveNote(title, description)
        }
    }
}
