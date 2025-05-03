package com.pedroluis.projects.notepad.features.detail.usecase.state

sealed class DetailUseCaseState {
    data object Success : DetailUseCaseState()
    data object ErrorTitle : DetailUseCaseState()
    data object ErrorDescription : DetailUseCaseState()
    data object ErrorGeneral : DetailUseCaseState()
}
