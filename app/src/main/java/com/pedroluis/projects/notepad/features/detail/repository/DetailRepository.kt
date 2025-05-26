package com.pedroluis.projects.notepad.features.detail.repository

import android.content.Context

internal interface DetailRepository {

    fun saveNote(context: Context, title: String, description: String)
    fun editNote(context: Context, id: String, title: String, description: String)
}