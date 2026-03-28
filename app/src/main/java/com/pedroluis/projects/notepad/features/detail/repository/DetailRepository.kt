package com.pedroluis.projects.notepad.features.detail.repository

internal interface DetailRepository {
    suspend fun saveNote(title: String, description: String)
    suspend fun editNote(id: String, title: String, description: String)
}