package com.pedroluis.projects.notepad.features.detail.usecase.state

sealed class DetailUseCaseState {
    object Success : DetailUseCaseState()
    object ErrorTitle : DetailUseCaseState()
    object ErrorDescription : DetailUseCaseState()
    object ErrorGeneral : DetailUseCaseState()
}
