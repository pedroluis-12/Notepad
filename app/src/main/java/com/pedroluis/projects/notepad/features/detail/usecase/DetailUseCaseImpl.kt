package com.pedroluis.projects.notepad.features.detail.usecase

import com.pedroluis.projects.notepad.features.detail.model.DetailErrorTypeEnum
import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.text.isEmpty
import kotlin.text.trim

class DetailUseCaseImpl : DetailUseCase {

    override fun saveNote(title: String, description: String): Flow<DetailUseCaseState> = flow {
        when {
            title.trim().isEmpty() && description.trim().isEmpty() -> {
                emit(DetailUseCaseState.Error(DetailErrorTypeEnum.EMPTY_BOTH))
                return@flow
            }

            title.trim().isEmpty() -> {
                emit(DetailUseCaseState.Error(DetailErrorTypeEnum.EMPTY_TITLE))
                return@flow
            }

            description.trim().isEmpty() -> {
                emit(DetailUseCaseState.Error(DetailErrorTypeEnum.EMPTY_DESCRIPTION))
                return@flow
            }
        }

        emit(DetailUseCaseState.Success)
    }
}
