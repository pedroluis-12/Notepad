package com.pedroluis.projects.notepad.features.detail.usecase

import com.pedroluis.projects.notepad.features.detail.usecase.state.DetailUseCaseState
import kotlinx.coroutines.flow.Flow

internal interface DetailUseCase {

    fun saveNote(
        index: Int? = null,
        title: String,
        description: String
    ): Flow<DetailUseCaseState>
}
