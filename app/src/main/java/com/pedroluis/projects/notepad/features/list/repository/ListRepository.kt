package com.pedroluis.projects.notepad.features.list.repository

import com.pedroluis.projects.notepad.commons.model.NotepadModel

internal interface ListRepository {
    suspend fun getNotes(): List<NotepadModel>
    suspend fun deleteNote(id: String)
}
