package com.pedroluis.projects.notepad.features.detail.usecase.state

import com.pedroluis.projects.notepad.features.detail.model.DetailErrorTypeEnum

sealed class DetailUseCaseState {
    object Success : DetailUseCaseState()
    data class Error(val errorType: DetailErrorTypeEnum) : DetailUseCaseState()
}
