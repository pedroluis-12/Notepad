package com.pedroluis.projects.notepad.features.detail.repository

internal interface DetailRepository {
    fun saveNote(title: String, description: String)
    fun editNote(id: String, title: String, description: String)
}