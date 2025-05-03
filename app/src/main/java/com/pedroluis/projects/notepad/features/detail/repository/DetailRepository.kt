package com.pedroluis.projects.notepad.features.detail.repository

internal interface DetailRepository {

    fun saveNote(title: String, description: String)
    fun editNote(index: Int, title: String, description: String)
}