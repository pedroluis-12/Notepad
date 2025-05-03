package com.pedroluis.projects.notepad.features.detail.usecase

import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailUseCaseImpl : DetailUseCase {

    override fun saveNote(title: String, description: String): Flow<DetailUseCaseState> = flow {
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
        }

        emit(DetailUseCaseState.Success)
    }
}
