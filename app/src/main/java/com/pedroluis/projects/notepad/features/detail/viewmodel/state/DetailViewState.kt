package com.pedroluis.projects.notepad.features.detail.viewmodel.state

internal sealed class DetailViewState {
    data object DisplaySuccess : DetailViewState()
    data object DisplayErrorTitle : DetailViewState()
    data object DisplayErrorDescription : DetailViewState()
    data object DisplayErrorGeneral : DetailViewState()
}
